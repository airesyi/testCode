import com.novell.ldap.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.*;

public class LDAPtest {

    private LdapContext connetLDAP() throws NamingException {
        // 连接Ldap需要的信息
        String ldapFactory = "com.sun.jndi.ldap.LdapCtxFactory";
        String ldapUrl = "ldap://domain.xinboxinmo.:389";// url
        String ldapAccount = "UMallscaner@xinboxinmo.com"; // 用户名
        String ldapPwd = "Xbxm2018***";//密码


//        String ldapUrl = "ldap://127.0.0.1:389";// url
//        String ldapAccount = ""; // 用户名
//        String ldapPwd = "";//密码


        // 邮箱，域，直接账户名都可登陆
//        String ldapAccount = "abc\\administrator"; // 用户名
//        String ldapPwd = "lwkj@2018";//密码
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, ldapFactory);
        // LDAP server
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapAccount);
        env.put(Context.SECURITY_CREDENTIALS, ldapPwd);
        env.put(Context.REFERRAL, "follow");
        LdapContext ctxTDS = new InitialLdapContext(env, null);
        return ctxTDS;
    }

    private LDAPConnection connetLDAP1() throws NamingException, LDAPException {
        LDAPConnection ld = new LDAPConnection(10000);
        // 连接Ldap需要的信息
        ld.connect("127.0.0.1", 389);
        ld.bind("t1@test.com", "1234567");

        return ld;
    }

    @Test
    public void testSearchA() throws Exception {
        LDAPConnection ld = connetLDAP1();
        String filter = "";
        filter = "(|(department=导购)(department=操作)(department=导购1))";

        String searchBase = "ou=guide,dc=test,dc=com";//域名入口
        int searchScope = LDAPConnection.SCOPE_SUBORDINATESUBTREE;//搜索范围

        LDAPSearchResults searchResults = ld.search(searchBase, searchScope, null, null, false);
        while (searchResults.hasMore()) {
            LDAPEntry nextEntry = searchResults.next();
//            System.out.println("We found "+nextEntry.getDN());
            LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
            Iterator ite = attributeSet.iterator();
            while (ite.hasNext()) {
                System.out.println(ite.next());
            }
        }
    }

    //查询
    @Test
    public void testSearch() throws Exception {
        LdapContext ctx = connetLDAP();
        // 设置过滤条件
        String uid = "yi.shi";
        String filter = "";
        filter = "(sAMAccountName=" + uid + ")";
//        filter = "(|(department=导购)(department=操作)(department=导购1))";
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
        List<NamingEnumeration<SearchResult>> list = new ArrayList<NamingEnumeration<SearchResult>>();

        NamingEnumeration<SearchResult> answer = ctx.search("cn=operate,dc=abc,dc=com", null);
        // 输出查到的数据
        while (answer.hasMore()) {
            SearchResult result = answer.next();

            System.out.println("------objectClass:" + result.getAttributes().get("objectClass"));
            System.out.println("------objectCategory:" + result.getAttributes().get("objectCategory"));

            NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
            while (attrs.hasMore()) {
                Attribute attr = attrs.next();
                System.out.println(attr.getID() + "=" + attr.get());
            }
            System.out.println("====================================================");
        }
    }

    @Test
    @Disabled
    public void testSearch1() throws Exception {
        LdapContext ctx = connetLDAP();
        // 设置过滤条件
        String uid = "t1";
        String filter = "(&(objectClass=top)(cn=" + uid + "))";
//        String filter = "(objectClass=top)";
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

        Object object = ctx.lookup("dc=tes,dc=com");
        // 输出查到的数据
        System.out.println(object.getClass());
        System.out.println(object);
    }

    // 添加
    @Test
    @Disabled
    public void testAdd() throws Exception {
        LdapContext ctx = connetLDAP();
        Attributes attrs = new BasicAttributes(true);
        Attribute objclass = new BasicAttribute("objectclass");
        // 添加ObjectClass
        String[] attrObjectClassPerson = {"inetOrgPerson", "organizationalPerson", "person", "top"};
        Arrays.sort(attrObjectClassPerson);
        for (String ocp : attrObjectClassPerson) {
            objclass.add(ocp);
        }
        attrs.put(objclass);
        String uid = "zhangsantestttttttt";
        String userDN = "uid=" + uid + "," + "cn=users,dc=abc,dc=com";
        // 密码处理
        // attrs.put("uid", uid);
        attrs.put("cn", uid);
        attrs.put("sn", uid);
        attrs.put("displayName", "张三");
        attrs.put("mail", "abc@163.com");
        attrs.put("description", "");
        attrs.put("userPassword", "Passw0rd".getBytes("UTF-8"));
        ctx.createSubcontext(userDN, attrs);
    }

    //修改
    public boolean testModify() throws Exception {
        boolean result = true;
        LdapContext ctx = connetLDAP();
        String uid = "zhangsan";
        String userDN = "uid=" + uid + "," + "cn=users,dc=cas,dc=mydc";
        Attributes attrs = new BasicAttributes(true);
        attrs.put("mail", "zhangsan@163.com");
        ctx.modifyAttributes(userDN, DirContext.REPLACE_ATTRIBUTE, attrs);
        return result;

    }

    //删除
    public void testRemove() throws Exception {
        LdapContext ctx = connetLDAP();
        String uid = "zhangsan";
        String userDN = "uid=" + uid + "," + "cn=users,dc=cas,dc=mydc";
        ctx.destroySubcontext(userDN);

    }
}
