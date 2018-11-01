import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class LDAPTestCode {
    private static final Logger logger = LoggerFactory.getLogger(LDAPTestCode.class);

    private static class LdapContextHolder {
        private static final LdapContext ctxTDS = LDAPTestCode.connetLDAP();
    }

    public static LdapContext getLdapContext() throws NamingException {
        return LdapContextHolder.ctxTDS;
    }

    static LdapContext connetLDAP() {
        // 连接Ldap需要的信息
        String ldapFactory = "com.sun.jndi.ldap.LdapCtxFactory";
//        String ldapUrl = "ldap://127.0.0.1:389";// url
        String ldapUrl = "ldap://172.16.10.96:389";// url
        String ldapAccount = "xinboxinmo//crmscaner"; // 用户名
        String ldapPwd = "w3H!1GRx4G@u";//密码
        // 邮箱，域，直接账户名都可登陆
//        String ldapAccount = "abc\\administrator"; // 用户名
//        String ldapPwd = "123456";//密码
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, ldapFactory);
        // LDAP server
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapAccount);
        env.put(Context.SECURITY_CREDENTIALS, ldapPwd);
        env.put(Context.REFERRAL, "follow");
        LdapContext ctxTDS = null;
        try {
            ctxTDS = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            logger.error("error", e);
        }

        return ctxTDS;
    }

    //查询
    @Test
    public void testSearch() throws Exception {

        // 设置过滤条件
        String uid = "o1";
        String filter = "";
        filter = "(cn=" + uid + ")";
        filter = "(|(department=导购)(department=操作)(department=导购1))";
//        filter = "(objectClass=top)";
        // 限制要查询的字段内容
//        String[] attrPersonArray = { "uid", "userPassword", "displayName", "cn", "sn", "mail", "description" };
        String[] attrPersonArray = {"cn", "sn", "name"};
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // 设置将被返回的Attribute
        searchControls.setReturningAttributes(attrPersonArray);
        // 三个参数分别为：
        // 上下文；
        // 要搜索的属性，如果为空或 null，则返回目标上下文中的所有对象；
        // 控制搜索的搜索控件，如果为 null，则使用默认的搜索控件
        //       NamingEnumeration<SearchResult> answer = ctx.search("cn=administrator,cn=users,dc=abc,dc=com", filter.toString(), null);
//        NamingEnumeration<SearchResult> answer = ctx.search("cn=Users,dc=abc,dc=com",  filter.toString(),   null);
//        NamingEnumeration<SearchResult> answer = ctx.search("cn=operate,dc=abc,dc=com", filter,null);

        List<String> result = new ArrayList<>();

        scanLDAP("OU=新博新美集团,DC=xinboxinmo,DC=com", result);

        System.out.println(result.size());
        System.out.println(result);
    }

    private static void scanLDAP(String name, List<String> resultList) throws Exception {
        LdapContext ctx = getLdapContext();
        NamingEnumeration<SearchResult> answer = ctx.search(name, null);
        // 输出查到的数据
        while (answer.hasMore()) {
            SearchResult result = answer.next();

            Attributes attribute = result.getAttributes();

            if (!attribute.get("objectClass").contains("person")) {
                String scanName = attribute.get("distinguishedName").toString();
                scanLDAP(scanName.split(":")[1], resultList);
            } else {
                resultList.add(attribute.get("displayName").toString());
                NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                while (attrs.hasMore()) {
                    Attribute attr = attrs.next();
                    System.out.println(attr.getID() + "=" + attr.get());
                }
                System.out.println("====================================================");
            }
        }
    }

}
