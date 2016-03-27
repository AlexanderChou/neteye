# $Id: neteye-web.spec,v 1.62 2012/06/20 02:37:40 hqzhu Exp $

# Authority: hqzhu
# Upstream: hqzhu <hqzhu@cernet.edu.cn>

%define build_stamp %(build_stamp=${time_stamp:-`date +%y%m%d_%H%M%%S`}; echo $build_stamp)
%define install_path /opt
%define realname NetEye
%define file_path WebContent
%define JAVA_ENDORSED_DIRS /usr/share/tomcat5/common/endorsed

Summary: JSP application for neteye project
Name: neteye-web
Version: 1.0
Release: %{build_stamp}
License: inetboss software distribution(BSD like)
Group: Neteye
URL: http://www.inetboss.com/neteye/

Packager: hqzhu <hqzhu@cenret.edu.cn>
Vendor: Inetboss Apt Repository, http://www.inetboss.com/neteye/

Source: %{name}.tar.gz
BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root

BuildArch: i386
BuildRequires: ant,tomcat5 

%description
JSP application for neteye project . This project is a network-manager system.

%package backbone
Summary:    neteye project for backbone
License:    inetboss software distribution(BSD like)
Group:      Neteye
Requires:   tomcat5 , patch , fonts-chinese 

%description backbone
neteye for backbone network 

%package campus
Summary:    neteye project for campus
License:    inetboss software distribution(BSD like)
Group:      Neteye
Requires:   tomcat5 , patch , fonts-chinese 

%description campus
neteye for campus network

%prep
%setup -n %{name}

%build
/usr/bin/ant

%install 
%{__rm} -rf %{buildroot}
install -D -m644 doc/neteye-release %{buildroot}/%{_sysconfdir}/neteye-release
sed -i s/__VERSION__/%{version}/g  %{buildroot}/%{_sysconfdir}/neteye-release
sed -i s/__RELEASE__/%{release}/g  %{buildroot}/%{_sysconfdir}/neteye-release
install -D -m644 doc/COPYING %{buildroot}%{_defaultdocdir}/%{name}/COPYING
install -D -m644 doc/LOGDIR %{buildroot}/opt/log/LOGDIR
install -D tmp/server.xml.patch %{buildroot}%{install_path}/%{realname}/tmp/server.xml.patch
install -D tmp/tomcat5.conf.patch %{buildroot}%{install_path}/%{realname}/tmp/tomcat5.conf.patch
install -D tmp/limits.conf.patch %{buildroot}%{install_path}/%{realname}/tmp/limits.conf.patch
install -D tmp/login.patch %{buildroot}%{install_path}/%{realname}/tmp/login.patch
install -D tmp/tomcat5.patch %{buildroot}%{install_path}/%{realname}/tmp/tomcat5.patch
cd %{file_path}
install -D build.xml %{buildroot}%{install_path}/%{realname}/build.xml
install -D index.html %{buildroot}%{install_path}/%{realname}/index.html
%{__mkdir_p} %{buildroot}%{install_path}/%{realname}/WEB-INF/lib/
%{__cp} -a WEB-INF/lib/*.jar %{buildroot}%{install_path}/%{realname}/WEB-INF/lib/
%{__mkdir_p}  %{buildroot}%{install_path}/%{realname}/css/
%{__cp} -a css/* %{buildroot}%{install_path}/%{realname}/css/
%{__mkdir_p} %{buildroot}%{install_path}/%{realname}/file/
%{__cp} -a file/* %{buildroot}%{install_path}/%{realname}/file/
%{__mkdir_p} %{buildroot}%{install_path}/%{realname}/mib/
%{__cp} -a mib/* %{buildroot}%{install_path}/%{realname}/mib/
%{__mkdir_p} %{buildroot}%{install_path}/%{realname}/images/
%{__cp} -a images/* %{buildroot}%{install_path}/%{realname}/images/
%{__mkdir_p} %{buildroot}%{install_path}/%{realname}/js/
%{__cp} -a js/* %{buildroot}%{install_path}/%{realname}/js/
%{__mkdir_p} %{buildroot}%{install_path}/%{realname}/flex/
%{__cp} -a flex/* %{buildroot}%{install_path}/%{realname}/flex/
%{__mkdir_p} %{buildroot}%{install_path}/%{realname}/subCSS/
%{__cp} -a subCSS/* %{buildroot}%{install_path}/%{realname}/subCSS/
install -D META-INF/MANIFEST.MF %{buildroot}%{install_path}/%{realname}/META-INF/MANIFEST.MF
install -D WEB-INF/web.xml %{buildroot}%{install_path}/%{realname}/WEB-INF/web.xml
install -D sys.conf %{buildroot}%{install_path}/%{realname}/sys.conf
%{__mkdir_p} %{buildroot}%{install_path}/%{realname}/WEB-INF/classes/
%{__cp} -a WEB-INF/classes/* %{buildroot}%{install_path}/%{realname}/WEB-INF/classes/
%{__mkdir_p} %{buildroot}%{install_path}/%{realname}/WEB-INF/pages/
%{__cp} -a WEB-INF/pages/* %{buildroot}%{install_path}/%{realname}/WEB-INF/pages/

%pre campus
echo "pre-install"
[ -e /opt/NetEye/sys.conf ] && mv -f /opt/NetEye/sys.conf /opt/NetEye/sys.conf.rpmold
echo "no conf1"
[ -e /opt/NetEye/file/syscfg.xml ] && mv -f /opt/NetEye/file/syscfg.xml /opt/NetEye/file/syscfg.xml.rpmold
echo "no conf2"

%post backbone
[ -e /etc/tomcat5/server.xml.old ] && rm -rf /etc/tomcat5/server.xml.old
[ -e /etc/tomcat5/server.xml ] && cp -af /etc/tomcat5/server.xml /etc/tomcat5/server.xml.old
echo "">/etc/tomcat5/server.xml
%{__patch} -p0 /etc/tomcat5/server.xml %{install_path}/%{realname}/tmp/server.xml.patch  >/dev/null 2>&1

[ -e /etc/security/limits.conf.old ] && rm -rf /etc/security/limits.conf.old 
[ -e /etc/security/limits.conf ] && cp -af /etc/security/limits.conf /etc/security/limits.conf.old 
echo "">/etc/security/limits.conf
%{__patch} -p0 /etc/security/limits.conf %{install_path}/%{realname}/tmp/limits.conf.patch >/dev/null 2>&1

[ -e /etc/pam.d/login.old ] && rm -rf /etc/pam.d/login.old
[ -e /etc/pam.d/login ] && cp -af /etc/pam.d/login /etc/pam.d/login.old
echo "">/etc/pam.d/login
%{__patch} -p0 /etc/pam.d/login %{install_path}/%{realname}/tmp/login.patch >/dev/null 2>&1

[ -e /etc/tomcat5/tomcat5.conf.old ] && rm -rf /etc/tomcat5/tomcat5.conf.old
[ -e /etc/tomcat5/tomcat5.conf ] && cp -af /etc/tomcat5/tomcat5.conf /etc/tomcat5/tomcat5.conf.old
echo "">/etc/tomcat5/tomcat5.conf
%{__patch} -p0 /etc/tomcat5/tomcat5.conf %{install_path}/%{realname}/tmp/tomcat5.conf.patch >/dev/null 2>&1

[ -e /etc/sysconfig/tomcat5.old ] && rm -rf /etc/sysconfig/tomcat5.old
[ -e /etc/sysconfig/tomcat5 ] && cp -af /etc/sysconfig/tomcat5 /etc/sysconfig/tomcat5.old
echo "">/etc/sysconfig/tomcat5
%{__patch} -p0 /etc/sysconfig/tomcat5 %{install_path}/%{realname}/tmp/tomcat5.patch >/dev/null 2>&1 

cd %{JAVA_ENDORSED_DIRS}
[ -e /usr/share/java/xalan-j2-2.7.0.jar ] && rm -rf [xalan-j2.jar].jar;ln -s /usr/share/java/xalan-j2-2.7.0.jar [xalan-j2.jar].jar
[ -e /usr/share/java/xalan-j2-serializer-2.7.0.jar ] && rm -rf [xalan-j2-serializer].jar;ln -s /usr/share/java/xalan-j2-serializer-2.7.0.jar [xalan-j2-serializer].jar

/sbin/service tomcat5 restart
/sbin/chkconfig --level 35 tomcat5 on

%post campus
[ -e /etc/tomcat5/server.xml.old ] && rm -rf /etc/tomcat5/server.xml.old
[ -e /etc/tomcat5/server.xml ] && cp -af /etc/tomcat5/server.xml /etc/tomcat5/server.xml.old
echo "">/etc/tomcat5/server.xml
%{__patch} -p0 /etc/tomcat5/server.xml %{install_path}/%{realname}/tmp/server.xml.patch  >/dev/null 2>&1

[ -e /etc/security/limits.conf.old ] && rm -rf /etc/security/limits.conf.old 
[ -e /etc/security/limits.conf ] && cp -af /etc/security/limits.conf /etc/security/limits.conf.old 
echo "">/etc/security/limits.conf
%{__patch} -p0 /etc/security/limits.conf %{install_path}/%{realname}/tmp/limits.conf.patch >/dev/null 2>&1

[ -e /etc/pam.d/login.old ] && rm -rf /etc/pam.d/login.old
[ -e /etc/pam.d/login ] && cp -af /etc/pam.d/login /etc/pam.d/login.old
echo "">/etc/pam.d/login
%{__patch} -p0 /etc/pam.d/login %{install_path}/%{realname}/tmp/login.patch >/dev/null 2>&1

[ -e /etc/tomcat5/tomcat5.conf.old ] && rm -rf /etc/tomcat5/tomcat5.conf.old
[ -e /etc/tomcat5/tomcat5.conf ] && cp -af /etc/tomcat5/tomcat5.conf /etc/tomcat5/tomcat5.conf.old
echo "">/etc/tomcat5/tomcat5.conf
%{__patch} -p0 /etc/tomcat5/tomcat5.conf %{install_path}/%{realname}/tmp/tomcat5.conf.patch >/dev/null 2>&1

[ -e /etc/sysconfig/tomcat5.old ] && rm -rf /etc/sysconfig/tomcat5.old
[ -e /etc/sysconfig/tomcat5 ] && cp -af /etc/sysconfig/tomcat5 /etc/sysconfig/tomcat5.old
echo "">/etc/sysconfig/tomcat5
%{__patch} -p0 /etc/sysconfig/tomcat5 %{install_path}/%{realname}/tmp/tomcat5.patch >/dev/null 2>&1 

[ -e /opt/NetEye/sys.conf.rpmold ] && mv -f /opt/NetEye/sys.conf.rpmold /opt/NetEye/sys.conf
[ -e /opt/NetEye/file/syscfg.xml.rpmold ] && mv -f /opt/NetEye/file/syscfg.xml.rpmold /opt/NetEye/file/syscfg.xml

cp -af %{install_path}/%{realname}/subCSS/css/*.css %{install_path}/%{realname}/css/
cp -af %{install_path}/%{realname}/subCSS/js/*.js %{install_path}/%{realname}/js/
cp -af %{install_path}/%{realname}/subCSS/WEB-INF/pages/common/*.jsp %{install_path}/%{realname}/WEB-INF/pages/common/
rm -rf %{install_path}/%{realname}/images/default
cp -af %{install_path}/%{realname}/subCSS/images/* %{install_path}/%{realname}/images
cp -af %{install_path}/%{realname}/subCSS/file/* %{install_path}/%{realname}/file
cd %{JAVA_ENDORSED_DIRS}
[ -e /usr/share/java/xalan-j2-2.7.0.jar ] && rm -rf [xalan-j2.jar].jar
ln -s /usr/share/java/xalan-j2-2.7.0.jar [xalan-j2.jar].jar
[ -e /usr/share/java/xalan-j2-serializer-2.7.0.jar ] && rm -rf [xalan-j2-serializer].jar
ln -s /usr/share/java/xalan-j2-serializer-2.7.0.jar [xalan-j2-serializer].jar

/sbin/chkconfig --level 35 tomcat5 on
/sbin/service tomcat5 restart

%preun backbone
[ -e /etc/tomcat5/server.xml.old ] && cp -af /etc/tomcat5/server.xml.old /etc/tomcat5/server.xml
[ -e /etc/security/limits.conf.old ] && cp -af /etc/security/limits.conf.old /etc/security/limits.conf 
[ -e /etc/pam.d/login.old ] && cp -af /etc/pam.d/login.old /etc/pam.d/login
[ -e /etc/tomcat5/tomcat5.conf.old ] && cp -af /etc/tomcat5/tomcat5.conf.old /etc/tomcat5/tomcat5.conf
[ -e /etc/sysconfig/tomcat5.old ] && cp -af /etc/sysconfig/tomcat5.old /etc/sysconfig/tomcat5
/sbin/service tomcat5 restart

%preun campus 
[ -e /etc/tomcat5/server.xml.old ] && cp -af /etc/tomcat5/server.xml.old /etc/tomcat5/server.xml
[ -e /etc/security/limits.conf.old ] && cp -af /etc/security/limits.conf.old /etc/security/limits.conf 
[ -e /etc/pam.d/login.old ] && cp -af /etc/pam.d/login.old /etc/pam.d/login
[ -e /etc/tomcat5/tomcat5.conf.old ] && cp -af /etc/tomcat5/tomcat5.conf.old /etc/tomcat5/tomcat5.conf
[ -e /etc/sysconfig/tomcat5.old ] && cp -af /etc/sysconfig/tomcat5.old /etc/sysconfig/tomcat5
[ -e /opt/NetEye/sys.conf ] && cp -af /opt/NetEye/sys.conf /opt/NetEye/sys.conf.rpmold
[ -e /opt/NetEye/file/syscfg.xml ] && cp -af /opt/NetEye/file/syscfg.xml /opt/NetEye/file/syscfg.xml.rpmold
/sbin/service tomcat5 restart

%postun campus 
[ -e /opt/NetEye/sys.conf.rpmold ] && mv -f /opt/NetEye/sys.conf.rpmold /opt/NetEye/sys.conf
[ -e /opt/NetEye/file/syscfg.xml.rpmold ] && mv -f /opt/NetEye/file/syscfg.xml.rpmold /opt/NetEye/file/syscfg.xml

%clean
%{__rm} -rf %{buildroot}

%files backbone
%defattr(-,root,root)
%{_sysconfdir}/neteye-release
%{_defaultdocdir}/%{name}/COPYING
/opt/log/LOGDIR
%{install_path}/%{realname}/WEB-INF/lib/*.jar
%{install_path}/%{realname}/tmp/server.xml.patch
%{install_path}/%{realname}/tmp/tomcat5.patch
%{install_path}/%{realname}/tmp/limits.conf.patch
%{install_path}/%{realname}/tmp/login.patch
%{install_path}/%{realname}/tmp/tomcat5.conf.patch
%{install_path}/%{realname}/index.html
%{install_path}/%{realname}/build.xml
%{install_path}/%{realname}/sys.conf
%{install_path}/%{realname}/css/*
%{install_path}/%{realname}/file/*
%{install_path}/%{realname}/mib/*
%{install_path}/%{realname}/images/*
%{install_path}/%{realname}/js/*
%{install_path}/%{realname}/flex/*
%{install_path}/%{realname}/META-INF/MANIFEST.MF
%{install_path}/%{realname}/WEB-INF/web.xml
%{install_path}/%{realname}/WEB-INF/classes/*
%{install_path}/%{realname}/WEB-INF/pages/*

%files campus 
%defattr(-,root,root)
%{_sysconfdir}/neteye-release
%{_defaultdocdir}/%{name}/COPYING
/opt/log/LOGDIR
%{install_path}/%{realname}/subCSS/*
%{install_path}/%{realname}/WEB-INF/lib/*.jar
%{install_path}/%{realname}/tmp/server.xml.patch
%{install_path}/%{realname}/tmp/tomcat5.patch
%{install_path}/%{realname}/tmp/limits.conf.patch
%{install_path}/%{realname}/tmp/login.patch
%{install_path}/%{realname}/tmp/tomcat5.conf.patch
%{install_path}/%{realname}/index.html
%{install_path}/%{realname}/build.xml
%{install_path}/%{realname}/sys.conf
%{install_path}/%{realname}/css/*
%{install_path}/%{realname}/file/*
%{install_path}/%{realname}/mib/*
%{install_path}/%{realname}/images/*
%{install_path}/%{realname}/js/*
%{install_path}/%{realname}/flex/*
%{install_path}/%{realname}/META-INF/MANIFEST.MF
%{install_path}/%{realname}/WEB-INF/web.xml
%{install_path}/%{realname}/WEB-INF/classes/*
%{install_path}/%{realname}/WEB-INF/pages/*


%changelog
* Wed Sep  2 2009 hqzhu <hqzhu@cernet.edu.cn> - 1.0-1 - /hqzhu
- Initial package.
