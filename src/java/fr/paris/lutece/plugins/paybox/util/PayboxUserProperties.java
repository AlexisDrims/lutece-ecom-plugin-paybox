/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.paybox.util;

import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * User configurable properties (cf paybox.properties).
 * Load every needed configuration variables from paybox.properties.
 */
public final class PayboxUserProperties
{
    /** The Constant ANNULE. */
    public static final String ANNULE = AppPropertiesService.getProperty( "paybox.annule" );

    /** The Constant BACKUP1. */
    public static final String BACKUP1 = AppPropertiesService.getProperty( "paybox.backup1" );

    /** The Constant BACKUP2. */
    public static final String BACKUP2 = AppPropertiesService.getProperty( "paybox.backup2" );

    /** The Constant DEVISE. */
    public static final String DEVISE = AppPropertiesService.getProperty( "paybox.devise" );

    /** The Constant EFFECTUE. */
    public static final String EFFECTUE = AppPropertiesService.getProperty( "paybox.effectue" );

    /** The Constant IDENTIFIANT. */
    public static final String IDENTIFIANT = AppPropertiesService.getProperty( "paybox.identifiant" );

    /** The Constant KEY. */
    public static final String KEY = AppPropertiesService.getProperty( "paybox.key" );

    /** The Constant PAYBOX. */
    public static final String PAYBOX = AppPropertiesService.getProperty( "paybox.paybox" );

    /** Chemin vers la clé publique sur le disque.. */
    public static final String PUBLIC_KEY_PATH = AppPropertiesService.getProperty( "paybox.publicKeyPath" );

    /** The Constant RANG. */
    public static final String RANG = AppPropertiesService.getProperty( "paybox.rang" );

    /** The Constant REFUSE. */
    public static final String REFUSE = AppPropertiesService.getProperty( "paybox.refuse" );

    /** The Constant REPONDRA. */
    public static final String REPONDRA = AppPropertiesService.getProperty( "paybox.repondrea" );

    /** The Constant RETOUR. */
    public static final String RETOUR = AppPropertiesService.getProperty( "paybox.retour" );

    /** The Constant SITE. */
    public static final String SITE = AppPropertiesService.getProperty( "paybox.site" );

    /** The Constant URL. */
    public static final String URL = AppPropertiesService.getProperty( "paybox.url" );

    /**
     * Instantiates a new paybox user properties.
     */
    private PayboxUserProperties(  )
    {
    }
}
