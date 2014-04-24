/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.paybox.item;


/**
 * The Class PayboxUrlItem contains data needed for paybox url building
 */
public class PayboxUrlItem
{
    /** The _amount in cents. */
    private Long _amountInCents;

    /** The _email. */
    private String _email;

    /** The _order reference. */
    private String _orderReference;

    /**
     * Gets the amount in cents.
     *
     * @return the amountInCents
     */
    public Long getAmountInCents(  )
    {
        return this._amountInCents;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail(  )
    {
        return this._email;
    }

    /**
     * Gets the order reference.
     *
     * @return the orderReference
     */
    public String getOrderReference(  )
    {
        return this._orderReference;
    }

    /**
     * Sets the amount in cents.
     *
     * @param amountInCents the amountInCents to set
     */
    public void setAmountInCents( final Long amountInCents )
    {
        this._amountInCents = amountInCents;
    }

    /**
     * Sets the email.
     *
     * @param email the email to set
     */
    public void setEmail( final String email )
    {
        this._email = email;
    }

    /**
     * Sets the order reference.
     *
     * @param orderReference the orderReference to set
     */
    public void setOrderReference( final String orderReference )
    {
        this._orderReference = orderReference;
    }
}
