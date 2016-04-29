<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.util.UUID"%>
<%@page import="java.security.interfaces.RSAPublicKey"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.hyeb.Setting"%>
<%@page import="com.hyeb.util.SettingUtils"%>
<%@page import="com.hyeb.util.SpringUtils"%>
<%@page import="com.hyeb.Setting.CaptchaType"%>
<%@page import="com.hyeb.Setting.AccountLockType"%>
<%@page import="java.security.KeyPair"%>
<%@page import="java.security.interfaces.RSAPrivateKey"%>
<%@page import="java.security.interfaces.RSAPublicKey"%>
<%@page import="com.hyeb.util.RSAUtils"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<%
String base = request.getContextPath();
String captchaId = UUID.randomUUID().toString();
Setting setting = SettingUtils.get();
	
/** "私钥"参数名称 */
final String PRIVATE_KEY_ATTRIBUTE_NAME = "privateKey";

KeyPair keyPair = RSAUtils.generateKeyPair();
RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();		
request.getSession().setAttribute(PRIVATE_KEY_ATTRIBUTE_NAME, privateKey);		


String modulus = Base64.encodeBase64String(publicKey.getModulus().toByteArray());//N
String exponent = Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray());//e

String message = null;
String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
if (loginFailure != null) {
	if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException")) {//无效令牌，验证码错误
		message = "admin.captcha.invalid";
	} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {//无此用户
		message = "admin.login.unknownAccount";
	} else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {//禁用用户
		message = "admin.login.disabledAccount";//
	} else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {//锁定用户
		message = "admin.login.lockedAccount";
	} else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {//密码不正月
		if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
			message = "admin.login.accountLockCount";//密码错误，若连续{0}次密码错误账号将被锁定
		} else {
			message = "admin.login.incorrectCredentials";//用户名或密码错误
		}
	} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {//其他未指定异常
		message = "admin.login.authentication";//账号认证失败
	}
}
%>
<title><%=SpringUtils.getMessage("admin.login.title")%> - Powered By SHOP++</title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="author" content="hyeb" />
<meta name="copyright" content="hyeb" />
<link href="<%=base%>/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>/resources/admin/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=base%>/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/jsbn.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/prng4.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/rng.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/rsa.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/base64.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/common.js"></script>
<script type="text/javascript">
	$().ready( function() {
		
		var $loginForm = $("#loginForm");
		var $enPassword = $("#enPassword");
		var $username = $("#username");
		var $password = $("#password");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		var $isRememberUsername = $("#isRememberUsername");
		
		// 记住用户名
		if(getCookie("adminUsername") != null) {
			$isRememberUsername.prop("checked", true);
			$username.val(getCookie("adminUsername"));
			$password.focus();
		} else {
			$isRememberUsername.prop("checked", false);
			$username.focus();
		}
		
		// 更换验证码
		$captchaImage.click( function() {
			$captchaImage.attr("src", "<%=base%>/admin/common/captcha.jhtml?captchaId=<%=captchaId%>&timestamp=" + (new Date()).valueOf());
		});
		
		// 表单验证、记住用户名
		$loginForm.submit( function() {
			if ($username.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.usernameRequired")%>");
				return false;
			}
			if ($password.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.passwordRequired")%>");
				return false;
			}
			if ($captcha.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.captchaRequired")%>");
				return false;
			}
			
			if ($isRememberUsername.prop("checked")) {
				addCookie("adminUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
			} else {
				removeCookie("adminUsername");
			}
			
			var rsaKey = new RSAKey();
			rsaKey.setPublic(b64tohex("<%=modulus%>"), b64tohex("<%=exponent%>"));
			var enPassword = hex2b64(rsaKey.encrypt($password.val()));
			$enPassword.val(enPassword);
		});
		
		<%if (message != null) {%>
			$.message("error", "<%=SpringUtils.getMessage(message, setting.getAccountLockCount())%>");
		<%}%>
	});
</script>


</head>
<body>
	<div class="login">
		<form id="loginForm" action="login.jsp" method="post">
			<input type="hidden" id="enPassword" name="enPassword" />
			<%if (ArrayUtils.contains(setting.getCaptchaTypes(), CaptchaType.adminLogin)) {%>
				<input type="hidden" name="captchaId" value="<%=captchaId%>" />
			<%}%>
			<table>
				<tr>
					<td width="190" rowspan="2" align="center" valign="bottom">
						<img src="<%=base%>/resources/admin/images/login_logo.gif" alt="SHOP++" />
					</td>
					<th>
						<%=SpringUtils.getMessage("admin.login.username")%>:
					</th>
					<td>
						<input type="text" id="username" name="username" class="text" maxlength="20" />
					</td>
				</tr>
				<tr>
					<th>
						<%=SpringUtils.getMessage("admin.login.password")%>:
					</th>
					<td>
						<input type="password" id="password" class="text" maxlength="20" autocomplete="off" />
					</td>
				</tr>
				<%if (ArrayUtils.contains(setting.getCaptchaTypes(), CaptchaType.adminLogin)) {%>
					<tr>
						<td>
							&nbsp;
						</td>
						<th>
							<%=SpringUtils.getMessage("admin.captcha.name")%>:
						</th>
						<td>
							<input type="text" id="captcha" name="captcha" class="text captcha" maxlength="4" autocomplete="off" /><img id="captchaImage" class="captchaImage" src="<%=base%>/admin/common/captcha.jhtml?captchaId=<%=captchaId%>" title="<%=SpringUtils.getMessage("admin.captcha.imageTitle")%>" />
						</td>
					</tr>
				<%}%>
				<tr>
					<td>
						&nbsp;
					</td>
					<th>
						&nbsp;
					</th>
					<td>
						<label>
							<input type="checkbox" id="isRememberUsername" value="true" />
							<%=SpringUtils.getMessage("admin.login.rememberUsername")%>:
						</label>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<th>
						&nbsp;
					</th>
					<td>
						<input type="button" class="homeButton" value="" onclick="location.href='<%=base%>/'" /><input type="submit" class="loginButton" value="<%=SpringUtils.getMessage("admin.login.login")%>" />
					</td>
				</tr>
			</table>
			<div class="powered">COPYRIGHT © 2005-2013 SHOPXX.NET ALL RIGHTS RESERVED.</div>
			<div class="link">
				<a href="<%=base%>/"><%=SpringUtils.getMessage("admin.login.home")%></a> |
				<a href="http://www.shopxx.net"><%=SpringUtils.getMessage("admin.login.official")%></a> |
				<a href="http://bbs.shopxx.net"><%=SpringUtils.getMessage("admin.login.bbs")%></a> |
				<a href="http://www.shopxx.net/about.html"><%=SpringUtils.getMessage("admin.login.about")%></a> |
				<a href="http://www.shopxx.net/contact.html"><%=SpringUtils.getMessage("admin.login.contact")%></a> |
				<a href="http://www.shopxx.net/license.html"><%=SpringUtils.getMessage("admin.login.license")%></a>
			</div>
		</form>
	</div>
</body>
</html>