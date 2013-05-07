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


/**
 * PayboxErrorConstants.
 * Contains paybox error constants.
 */
public final class PayboxErrorConstants
{
    /** Opération réussie */
    public static final String CODE_ERROR_OPERATION_REUSSIE = "00000";

    /**
     * La connexion au centre d’autorisation a échoué. Vous pouvez dans ce cas
     * là effectuer les redirections des internautes vers le FQDN
     * tpeweb1.paybox.com.
     */
    public static final String CODE_ERROR_CONNECTION_FAILED = "00001";

    /**
     * Paiement refusé par le centre d’autorisation [voir §12.1 Codes réponses
     * du centre d’autorisation].
     * En cas d’autorisation de la transaction par le centre d’autorisation de
     * la banque ou de l’établissement financier privatif, le code erreur
     * “00100�? sera en fait remplacé directement par “00000�?.
     */
    public static final String CODE_ERROR_CONNECTION_DENIED = "001xx";

    /**
     * Erreur Paybox.
     */
    public static final String CODE_ERROR_PAYBOX = "00003";

    /**
     * Numéro de porteur ou cryptogramme visuel invalide.
     */
    public static final String CODE_ERROR_WRONG_USER_NAME_OR_CRYPTOGRAM = "00004";

    /**
     * Accès refusé ou site/rang/identifiant incorrect.
     */
    public static final String CODE_ERROR_ACCESS_DENIED = "00006";

    /**
     * Date de fin de validité incorrecte.
     */
    public static final String CODE_ERROR_WRONG_EXPIRATION_DATE = "00008";

    /**
     * Erreur de création d’un abonnement.
     */
    public static final String CODE_ERROR_SUBSCRIPTION_CREATION = "00009";

    /**
     * Devise inconnue.
     */
    public static final String CODE_ERROR_UNKNOWN_CURRENCY = "00010";

    /**
     * Montant incorrect.
     */
    public static final String CODE_ERROR_WRONG_AMOUNT = "00011";

    /**
     * Paiement déjà effectué.
     */
    public static final String CODE_ERROR_ALREADY_DONE = "00015";

    /**
     * Abonné déjà existant (inscription nouvel abonné). Valeur ‘U’ de la
     * variable PBX_RETOUR.
     */
    public static final String CODE_ERROR_ALREADY_REGISTERED = "00016";

    /**
     * Carte non autorisée.
     */
    public static final String CODE_ERROR_CARD_NOT_ALLOWED = "00021";

    /**
     * Carte non conforme. Code erreur renvoyé lors de la documentation de la
     * variable « PBX_EMPREINTE ».
     */
    public static final String CODE_ERROR_CARD_INVALID = "00029";

    /**
     * Temps d’attente > 15 mn par l’internaute/acheteur au niveau de la page de
     * paiements.
     */
    public static final String CODE_ERROR_TIMEOUT = "00030";

    /**
     * Réservé
     */
    public static final String CODE_ERROR_RESERVED = "00031";

    /**
     * Réservé
     */
    public static final String CODE_ERROR_RESERVED_2 = "00032";

    /**
     * Code pays de l’adresse IP du navigateur de l’acheteur non autorisé.
     */
    public static final String CODE_ERROR_BLOCKED_IP = "00033";

    /**
     * Opération sans authentification 3DSecure, bloquée par le filtre.
     */
    public static final String CODE_ERROR_3DSECURE = "00040";

    /**
     * Instantiates a new paybox error constants.
     */
    private PayboxErrorConstants(  )
    {
    }
}
