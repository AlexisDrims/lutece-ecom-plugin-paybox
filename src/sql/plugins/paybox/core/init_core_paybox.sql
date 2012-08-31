INSERT INTO core_admin_right (id_right, 
name, level_right, admin_url, description, is_updatable, plugin_name, id_feature_group, icon_url, documentation_url, id_order)
VALUES ('PAYBOX_READ', 'paybox.adminFeature.name', 3, 'jsp/admin/plugins/paybox/ConsulterLogs.jsp', 'paybox.adminFeature.description', 0, 'paybox', 'APPLICATIONS', NULL, NULL, 14);
INSERT INTO core_user_right (id_right, id_user) VALUES ('PAYBOX_READ',1);