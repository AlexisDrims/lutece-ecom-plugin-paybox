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
package fr.paris.lutece.plugins.paybox.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.paris.lutece.plugins.paybox.PayboxUtil;
import fr.paris.lutece.plugins.paybox.service.PayboxLogService;
import fr.paris.lutece.plugins.paybox.service.PayboxPlugin;
import fr.paris.lutece.plugins.paybox.service.PayboxService;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * Handles paybox server to server.
 */
public class PayboxServlet extends HttpServlet
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The _paybox log service. */
    private final PayboxLogService _payboxLogService = SpringContextService.getBean( "paybox.payboxLogService" );

    /*
     * (non-Javadoc)
     *
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet( final HttpServletRequest req, final HttpServletResponse resp )
        throws ServletException, IOException
    {
        this.doPost( req, resp );
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost( final HttpServletRequest request, final HttpServletResponse response )
        throws ServletException, IOException
    {
        if ( AppPropertiesService.getPropertyBoolean( "paybox.check.sign.callback", true )
                && PayboxUtil.checkSignature( request.getQueryString( ) ) )
        {
            // ici maj du service de logs
            this._payboxLogService.removeLog( request.getParameter( "reference" ),
                PluginService.getPlugin( PayboxPlugin.PLUGIN_NAME ) );

            this.delagate( request, response );
        }
        else
        {
            AppLogService.error( "Problème de vérification de la signature." );
        }

        response.setStatus( 200 );
    }

    /**
     * Delagate.
     *
     * @param request the request
     * @param response the response
     */
    private void delagate( final HttpServletRequest request, final HttpServletResponse response )
    {
        // no DI possible...
        final List<PayboxService> listPayboxServices = SpringContextService.getBeansOfType( PayboxService.class );

        if ( ( listPayboxServices == null ) || listPayboxServices.isEmpty(  ) )
        {
            AppLogService.error( "No PayboxService bean defined. Please check your Spring configuration." );
        }
        else if ( listPayboxServices.size(  ) > 1 )
        {
            AppLogService.error( "Multiple PayboxService beans defined. Please check your Spring configuration." );
        }
        else
        {
            final PayboxService payboxService = listPayboxServices.get( 0 );
            AppLogService.debug( "Using " + payboxService.getClass(  ) + " as PayboxService" );

            try
            {
                payboxService.handlePayboxReturn( request, response );
            }
            catch ( final Exception e )
            {
                AppLogService.error( e );
            }
        }
    }
}
