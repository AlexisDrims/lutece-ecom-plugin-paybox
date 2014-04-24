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

import fr.paris.lutece.plugins.paybox.PayboxUtil;
import fr.paris.lutece.plugins.paybox.item.PayboxUrlItem;
import fr.paris.lutece.plugins.paybox.service.PayboxLogService;
import fr.paris.lutece.plugins.paybox.service.PayboxPlugin;
import fr.paris.lutece.plugins.paybox.service.PayboxService;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.web.system.PluginJspBean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * The Class PayboxApp.
 */
public class PayboxJspBean extends PluginJspBean
{
    /** The _paybox log service. */
    private final PayboxLogService _payboxLogService = SpringContextService.getBean( "paybox.payboxLogService" );

    /**
     * Gets the paybox informations about current user and redirect it
     *
     * @param request the request
     * @param response the response
     */
    public void getPayboxAccess( final HttpServletRequest request, final HttpServletResponse response )
    {
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
                final PayboxUrlItem payboxUrlItem = payboxService.handlePayboxAccess( request, response );

                if ( payboxUrlItem != null )
                {
                    this._payboxLogService.addLog( payboxUrlItem.getOrderReference(  ),
                        PluginService.getPlugin( PayboxPlugin.PLUGIN_NAME ) );
                    response.sendRedirect( PayboxUtil.buildPayboxUrl( payboxUrlItem ) );
                }
                else
                {
                    response.sendRedirect( request.getHeader( "Referer" ) );
                }
            }
            catch ( final Exception e )
            {
                AppLogService.error( e );
            }
        }
    }
}
