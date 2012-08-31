/*
 * Copyright (c) 2002-2011, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.paybox;

import fr.paris.lutece.plugins.paybox.item.PayboxUrlItem;
import fr.paris.lutece.plugins.paybox.util.PayboxConstants;
import fr.paris.lutece.plugins.paybox.util.PayboxUserProperties;
import fr.paris.lutece.portal.service.util.AppLogService;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

import org.bouncycastle.util.io.pem.PemReader;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.net.URLEncoder;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import javax.xml.bind.DatatypeConverter;


/**
 *
 * Utility class, provides help to create links to paybox and validates
 * answers from paybox.
 */
public final class PayboxUtil
{
    /** UTF-8 Charset. */
    private static final String CHARSET = "utf-8";

    /** Public Key encryption algorithm. */
    private static final String ENCRYPTION_ALGORITHM = "RSA";

    /** Signature hash encryption algorithm. */
    private static final String HASH_ENCRYPTION_ALGORITHM = "SHA1withRSA";

    /** Hash method used for hmac, send to paybox as a parameter. */
    private static final String HASH_METHOD = "Sha512";

    /** HMAC method used to sign paybox parameters. */
    private static final String HMAC_METHOD = "HmacSHA512";

    /**
     * private constructor.
     */
    private PayboxUtil(  )
    {
    }

    /**
     * Creates a key=value http GET parameter using set parameter.
     *
     * @param urlencode If true value will be urlencoded
     * @param set the key/value set
     * @return the key=value generated string.
     */
    private static String addKeyValueElement( final boolean urlencode, final Entry<String, String> set )
    {
        final StringBuilder stringBuilder = new StringBuilder(  );
        stringBuilder.append( set.getKey(  ) );
        stringBuilder.append( '=' );

        if ( urlencode )
        {
            try
            {
                stringBuilder.append( URLEncoder.encode( set.getValue(  ), "utf-8" ) );
            }
            catch ( final UnsupportedEncodingException e )
            {
                AppLogService.error( e );
            }
        }
        else
        {
            stringBuilder.append( set.getValue(  ) );
        }

        return stringBuilder.toString(  );
    }

    /**
     * Builds a full paybox access url.
     * This method is an help for nominal case, it does not fit for more
     * specific cases.
     *
     *
     * @param amountInCents the amount to be paid in cents.
     * @param orderReference the order reference number.
     * @param email user mail
     * @return the full url
     */
    public static String buildPayboxUrl( final Long amountInCents, final String orderReference, final String email )
    {
        final LinkedHashMap<String, String> params = new LinkedHashMap<String, String>(  );
        params.put( PayboxConstants.PBX_SITE, PayboxUserProperties.SITE );
        params.put( PayboxConstants.PBX_RANG, PayboxUserProperties.RANG );
        params.put( PayboxConstants.PBX_IDENTIFIANT, PayboxUserProperties.IDENTIFIANT );
        params.put( PayboxConstants.PBX_PAYBOX, PayboxUserProperties.PAYBOX );
        params.put( PayboxConstants.PBX_BACKUP1, PayboxUserProperties.BACKUP1 );
        params.put( PayboxConstants.PBX_BACKUP2, PayboxUserProperties.BACKUP2 );
        params.put( PayboxConstants.PBX_TOTAL, String.valueOf( amountInCents ) );
        params.put( PayboxConstants.PBX_DEVISE, PayboxUserProperties.DEVISE );
        params.put( PayboxConstants.PBX_CMD, orderReference );
        params.put( PayboxConstants.PBX_PORTEUR, email );
        params.put( PayboxConstants.PBX_RETOUR, PayboxUserProperties.RETOUR );
        params.put( PayboxConstants.PBX_EFFECTUE, PayboxUserProperties.EFFECTUE );
        params.put( PayboxConstants.PBX_REFUSE, PayboxUserProperties.REFUSE );
        params.put( PayboxConstants.PBX_ANNULE, PayboxUserProperties.ANNULE );
        params.put( PayboxConstants.PBX_REPONDRE_A, PayboxUserProperties.REPONDRA );

        return PayboxUtil.buildPayboxUrl( PayboxUserProperties.URL, params );
    }

    /**
     * Builds a full paybox access url.
     *
     * @param payboxUrlItem the paybox url item
     * @return the full url.
     */
    public static String buildPayboxUrl( final PayboxUrlItem payboxUrlItem )
    {
        return PayboxUtil.buildPayboxUrl( payboxUrlItem.getAmountInCents(  ), payboxUrlItem.getOrderReference(  ),
            payboxUrlItem.getEmail(  ) );
    }

    /**
     * Builds a full paybox access url.
     *
     * @param url Paybox url
     * @param params the params key/value map, Time, hash and hashmac parameters
     *            are automatically computed.
     * @return the full url.
     */
    public static String buildPayboxUrl( final String url, final LinkedHashMap<String, String> params )
    {
        // PBX_TIME, PBX_HASH and PBX_HMAC are removed before re-insertion to gurantee
        // that all of them are placed at the end of the uri and with relevant values.
        params.remove( PayboxConstants.PBX_TIME );
        params.remove( PayboxConstants.PBX_HASH );
        params.remove( PayboxConstants.PBX_HMAC );

        params.put( PayboxConstants.PBX_TIME, ISODateTimeFormat.dateHourMinuteSecond(  ).print( new DateTime(  ) ) );
        params.put( PayboxConstants.PBX_HASH, PayboxUtil.HASH_METHOD );
        params.put( PayboxConstants.PBX_HMAC, PayboxUtil.generateHMAC( PayboxUtil.join( params, false ) ) );

        final StringBuilder stringBuilder = new StringBuilder(  );
        stringBuilder.append( url );
        stringBuilder.append( '?' );
        stringBuilder.append( PayboxUtil.join( params, true ) );

        return stringBuilder.toString(  );
    }

    /**
     * Check signature by taking a full queryString and analyzing it.
     *
     * @param queryString the full query string
     * @return true if successful, false if not.
     */
    public static boolean checkSignature( final String queryString )
    {
        // splits parameters and keeps the latest which is the signature and previous which are signed data.
        final String[] params = queryString.split( "&" );
        final String[] paramsMinusOne = new String[params.length - 1];

        for ( int i = 0; i < ( params.length - 1 ); i++ )
        {
            paramsMinusOne[i] = params[i];
        }

        final String payboxParams = StringUtils.join( paramsMinusOne, "&" );

        // signature extraction
        final String sign = params[params.length - 1].split( "=" )[1];

        return PayboxUtil.checkSignature( payboxParams, sign, PayboxUserProperties.PUBLIC_KEY_PATH );
    }

    /**
     *
     * Check if provided signature is the one expected for given parameter using
     * paybox public key.
     *
     * @param message the message.
     * @param sign the signature.
     * @param keyPath public key path on file system.
     * @return true if signature is valid, false if not.
     */
    public static boolean checkSignature( final String message, final String sign, final String keyPath )
    {
        boolean ret;

        try
        {
            ret = PayboxUtil.verify( message, sign, PayboxUtil.getKey( keyPath ) );
        }
        catch ( final FileNotFoundException e )
        {
            ret = false;
            AppLogService.error( e );
        }
        catch ( final IOException e )
        {
            ret = false;
            AppLogService.error( e );
        }
        catch ( final NoSuchAlgorithmException e )
        {
            ret = false;
            AppLogService.error( e );
        }
        catch ( final InvalidKeySpecException e )
        {
            ret = false;
            AppLogService.error( e );
        }
        catch ( final InvalidKeyException e )
        {
            ret = false;
            AppLogService.error( e );
        }
        catch ( final SignatureException e )
        {
            ret = false;
            AppLogService.error( e );
        }

        return ret;
    }

    /**
     * Generates hmac signature of a message.
     *
     * @param payboxParameters parameters concatenation in the pattern
     *            key1=value1&key2=value2&...&keyN=valueN of all transmited
     *            parameter, not urlencoded
     * @return the signature (it will be added as value of the latest
     *         parameter).
     */
    private static String generateHMAC( final String payboxParameters )
    {
        Mac mac;
        String result;

        try
        {
            final byte[] bytesKey = DatatypeConverter.parseHexBinary( PayboxUserProperties.KEY );
            final SecretKeySpec secretKey = new SecretKeySpec( bytesKey, PayboxUtil.HMAC_METHOD );
            mac = Mac.getInstance( PayboxUtil.HMAC_METHOD );
            mac.init( secretKey );

            final byte[] macData = mac.doFinal( payboxParameters.getBytes( PayboxUtil.CHARSET ) );
            final byte[] hex = new Hex(  ).encode( macData );
            result = new String( hex, PayboxUtil.CHARSET );
        }
        catch ( final NoSuchAlgorithmException e )
        {
            result = "";
            AppLogService.error( e );
        }
        catch ( final InvalidKeyException e )
        {
            result = "";
            AppLogService.error( e );
        }
        catch ( final UnsupportedEncodingException e )
        {
            result = "";
            AppLogService.error( e );
        }

        return result.toUpperCase(  );
    }

    /**
     *
     * Get public key at specified path.
     *
     * @param keyPath public key path.
     * @return public key object.
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InvalidKeySpecException the invalid key spec exception
     */
    private static PublicKey getKey( final String keyPath )
        throws NoSuchAlgorithmException, IOException, InvalidKeySpecException
    {
        final KeyFactory keyFactory = KeyFactory.getInstance( PayboxUtil.ENCRYPTION_ALGORITHM );
        final PemReader reader = new PemReader( new FileReader( keyPath ) );
        final byte[] pubKey = reader.readPemObject(  ).getContent(  );
        final X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec( pubKey );

        PublicKey generatePublic = keyFactory.generatePublic( publicKeySpec );
        reader.close(  );

        return generatePublic;
    }

    /**
     * Assemble tous les parametres pour en faire une chaine cle-valeur.
     *
     * Joins all parameters together
     *
     * @param params params key/value map.
     * @param urlencode if true values are urlencoded.
     * @return joined key and values in a single string.
     */
    private static String join( final LinkedHashMap<String, String> params, final boolean urlencode )
    {
        final String[] elems = new String[params.size(  )];
        int index = 0;

        for ( final Entry<String, String> set : params.entrySet(  ) )
        {
            elems[index++] = PayboxUtil.addKeyValueElement( urlencode, set );
        }

        return StringUtils.join( elems, '&' );
    }

    /**
     *
     * Operates validation of signature among message and public key.
     *
     * @param message the message
     * @param sign the raw signature, must be still urlencoded.
     * @param publicKey the public key.
     * @return true, if signature successfully validated.
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws InvalidKeyException the invalid key exception
     * @throws SignatureException the signature exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    private static boolean verify( final String message, final String sign, final PublicKey publicKey )
        throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException
    {
        final Signature sig = Signature.getInstance( PayboxUtil.HASH_ENCRYPTION_ALGORITHM );
        sig.initVerify( publicKey );
        sig.update( message.getBytes( PayboxUtil.CHARSET ) );

        final Base64 b64 = new Base64(  );
        final byte[] bytes = b64.decode( URLDecoder.decode( sign, PayboxUtil.CHARSET ).getBytes( PayboxUtil.CHARSET ) );

        return sig.verify( bytes );
    }
}
