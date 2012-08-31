/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import fr.paris.lutece.plugins.paybox.dto.PayboxLogDTO;
import fr.paris.lutece.plugins.paybox.service.PayboxLogService;
import fr.paris.lutece.plugins.paybox.service.PayboxPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class PayboxAdminJspBean.
 */
public class PayboxAdminJspBean extends PluginAdminPageJspBean
{
    /** The Constant RIGHT_READ. */
    public static final String RIGHT_READ = "PAYBOX_READ";

    /** The Constant FREEMARKER_TABLE. */
    private static final String FREEMARKER_TABLE = "table";

    /** The Constant PROPERTY_PAGE_TITLE_PAYBOX. */
    private static final String PROPERTY_PAGE_TITLE_PAYBOX = "paybox.adminFeature.pageTitle";

    /** The Constant TEMPLATE. */
    private static final String TEMPLATE = "admin/plugins/paybox/paybox_read_log.html";
    private final PayboxLogService _payboxLogService = SpringContextService.getBean( "paybox.payboxLogService" );

    /**
     * Gets the manage paris video.
     *
     * @param request the request
     * @return the manage paris video
     */
    public String getLogs( final HttpServletRequest request )
    {
        this.setPageTitleProperty( PayboxAdminJspBean.PROPERTY_PAGE_TITLE_PAYBOX );

        final Map<String, Object> model = new HashMap<String, Object>(  );
        final List<PayboxLogDTO> all = this._payboxLogService.getAll( PluginService.getPlugin( PayboxPlugin.PLUGIN_NAME ) );
        model.put( PayboxAdminJspBean.FREEMARKER_TABLE, all );

        final HtmlTemplate templateList = AppTemplateService.getTemplate( PayboxAdminJspBean.TEMPLATE,
                this.getLocale(  ), model );

        return this.getAdminPage( templateList.getHtml(  ) );
    }
}
