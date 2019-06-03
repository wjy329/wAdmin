package com.wjy329.wshiro.service.impl;/**
 * @Author wjy329
 * @Time 2019/1/139:20 PM
 * @description
 */

import com.alibaba.druid.sql.visitor.functions.Ltrim;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wjy329.wcommon.constant.Constants;
import com.wjy329.wcommon.dto.SystemPageContext;
import com.wjy329.wshiro.dao.PermissionDao;
import com.wjy329.wshiro.dao.RolePermDao;
import com.wjy329.wshiro.entity.Permission;
import com.wjy329.wshiro.entity.RolePerm;
import com.wjy329.wshiro.entity.User;
import com.wjy329.wshiro.model.LTree;
import com.wjy329.wshiro.model.Menus;
import com.wjy329.wshiro.model.Tree;
import com.wjy329.wshiro.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2019-01-13 21:20
 **/
@Service
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermDao rolePermDao;

    @Override
    public List<Menus> getMenuNavs() {
        List<Permission> entitys = null;
        List<Permission> result = new ArrayList<>();
        //当是管理员的情况,获取所有的菜单信息
        if(SecurityUtils.getSubject().hasRole(Constants.ADMIN_ROLE)){
            entitys = this.permissionDao.getAllMenuUrls();
            for(Permission entity : entitys){
                if(entity.getPid().intValue() != 0){
                    result.add(entity);
                }
            }
        }else{
            //其他系统的情况，根据用户的id来查询菜单信息
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.LOGIN_USER);
            //根据用户的id来查找到菜单信息
            entitys = this.permissionDao.getMenuUrlsByUid(user.getUid());
            for(Permission entity : entitys){
                if(entity.getPid().intValue() != 0){
                    result.add(entity);
                }
            }
        }

        if(result ==null || result.size() ==0 ){
            return null;
        }

        return this.convert2NavMenu(result);
    }

    @Override
    public List<Tree> getTreeData() {
        List<Permission> entitys = this.permissionDao.getAllUrls();
        //父类id
        Set<Long> parentIds = new HashSet<>();
        for(Permission entity : entitys){
           parentIds.add(entity.getParentId());
        }

        List<Tree> results = new ArrayList<>();
        for(Permission entity : entitys){
            Tree tree = new Tree();
            if(parentIds.contains(entity.getPid())){
                tree.setOpen(true);
            }
            tree.setId(entity.getPid());
            tree.setpId(entity.getParentId());
            tree.setName(entity.getTitle());
            results.add(tree);
        }

        return results;
    }

    @Override
    public void updateMenu(Long id, Long newId) {
//        Permission permission = permissionDao.getByPid(id);
//        Permission newPerm = permissionDao.getByPid(newId);
//        //同一个父节点下的
//        if(permission.getParentId().equals(newPerm.getParentId()) ){
//
//        }
    }

    @Override
    public JSONArray queryByPage(Long pid) {

        Integer total = 0;
        List<Permission> result = null;
        if(pid == null){
            total =  this.permissionDao.getAllParentCnt();
            result = this.permissionDao.queryPageParent(SystemPageContext.getPageInfo());
        }else{
            total =  this.permissionDao.getAllChildCnt(pid);
            result = this.permissionDao.queryPageChilden(SystemPageContext.getPageInfo(), pid);
        }
        // 设置总数目
        SystemPageContext.setTotal(total);

        JSONArray grid = new JSONArray();
        if (result == null) {
            return grid;
        }

        return (JSONArray) JSONArray.parse(JSON.toJSON(result).toString());
    }

    @Override
    public void update(Permission permission) {
        Permission entity = new Permission();
        entity.setPid(permission.getPid());
        entity.setHref(permission.getHref());
        entity.setTitle(permission.getTitle());
        entity.setOrders(permission.getOrders());
        entity.setIcon(permission.getIcon());
        entity.setType(permission.getType());
        this.permissionDao.update(entity);
    }

    @Override
    public Set<Permission> getPermByRid(Integer rid) {
        Set<Permission> result = new HashSet<>();
        List<Permission> permissions = permissionDao.getPermByRid(rid);
        result.addAll(permissions);
        return result;
    }

    @Override
    public Map<String, String> getUrlPerm() {
        Map<String,String> urlPerm = new HashMap<>();
        List<Permission> permissions = permissionDao.getAllPerm();

        for(Permission permission : permissions){
            if(permission.getHref() != null && !permission.getHref().equals("")){
                urlPerm.put(permission.getHref(),shiroFormat(permission.getCode()));
            }
        }
        return urlPerm;
    }

    @Override
    public void insertPerm(Permission permission) {
        Permission entity = new Permission();
        entity.setParentId(permission.getPid());
        String href =permission.getHref();
        if(href != null && !href.startsWith("/")){
            href ="/" +  href;
        }
        entity.setHref(href);
        entity.setTitle(permission.getTitle());
        entity.setCode(permission.getCode());
        entity.setOrders(permission.getOrders());
        entity.setType(permission.getType());
        //设定默认的按钮
        String icon = permission.getIcon();
        entity.setIcon(StringUtils.isEmpty(icon)?Constants.DEFAULT_ICON:icon);
        this.permissionDao.insert(entity);
        //新增菜单加入admin role_perm
        Integer pid = entity.getPid().intValue();
        RolePerm rolePerm = new RolePerm();
        rolePerm.setrId(Constants.ADMIN_ID);
        rolePerm.setpId(pid);
        rolePermDao.insert(rolePerm);
    }

    @Transactional
    @Override
    public void deletePerms(String id) {
        //删除菜单关联的role_perm表
        this.permissionDao.deleteRolePerms(id);
        //删除菜单
        this.permissionDao.deletePerms(id);

    }

    @Override
    public List<RolePerm> getSelectedByRid(Integer rid) {
       List<RolePerm> rolePerms = rolePermDao.getRolePermByRid(rid);
       return rolePerms;
    }

    @Override
    public JSONArray queryMenuByPage(Long id) {
        Integer total = 0;
        List<Permission> result = null;
        total =  this.permissionDao.getPageCntById(id);
        result = this.permissionDao.queryPageById(SystemPageContext.getPageInfo(), id);
        // 设定总的数据两
        SystemPageContext.setTotal(total);
        JSONArray grid = new JSONArray();
        if (result == null) {
            return grid;
        }
        return (JSONArray) JSONArray.parse(JSON.toJSON(result).toString());
    }

    @Override
    public List<LTree> getLayTree() {
        List<LTree> treeList = new ArrayList<>();
        List<Permission> entitys = this.permissionDao.getAllUrls();
        Set<Long> parentIds = new HashSet<>();
        for(Permission permission : entitys){
            if(permission.getParentId() != null){
                parentIds.add(permission.getParentId());
            }else{
                // 所有的一级菜单
                LTree lTree = this.getLTree(permission);
                treeList.add(lTree);
            }
        }
        // 为一级菜单设置子菜单
        for(LTree lTree : treeList){
            lTree.setChildren(getChilds(lTree.getId(),entitys,parentIds));
        }
        return treeList;
    }

    private List<LTree> getChilds(Long pid, List<Permission> entitys,Set<Long> parentIds) {
        //子菜单
        List<LTree> childs = new ArrayList<>();
        for(Permission permission : entitys){
            // 判断是否是该节点的子菜单
            if(permission.getParentId() != null){
                if(permission.getParentId() == pid){
                    LTree lTree = this.getLTree(permission);
                    childs.add(lTree);
                }
            }
        }
	    // 把子菜单的子菜单再循环一遍
        for(LTree lTree : childs){
            if(parentIds.contains(lTree.getId())){
                lTree.setChildren(getChilds(lTree.getId(),entitys,parentIds));
            }
        }

	    // 递归退出条件
        if (childs.size() == 0) {
	        return null;
        }
	    return childs;
    }




    private LTree getLTree(Permission entity) {
        LTree lTree = new LTree();
        lTree.setId(entity.getPid());
        lTree.setDisabled(false);
        lTree.setFixed(true);
        lTree.setLabel(entity.getTitle());
        lTree.setHref(entity.getHref());
        return  lTree;
    }

    /** 
    * @description 返回shiro的格式， perms[user:add]
    * @param  
    * @return  
    * @author wjy329
    * @Date 2019/1/20 
    */ 
    private String shiroFormat(String code) {
        StringBuffer sb = new StringBuffer();
        sb.append("perms[");
        sb.append(code);
        sb.append("]");
        return sb.toString();
    }

    /**
         * @Date: 2018.12.7
         * @author: wjy329
         * 功能描述:将菜单转化为界面可识别的菜单<br/>
         * @param entitys
         * @return
         */
        private List<Menus> convert2NavMenu(List<Permission> entitys){

            //获取父类的parentId
            List<Permission> parents = new ArrayList<>();

            for(Permission entity:entitys){
                if(entity.getParentId() == null || entity.getParentId() ==0){
                    parents.add(entity);
                }
            }

            List<Menus> menus = new ArrayList<Menus>();
            //获取子类的信息
            for(Permission parent:parents){

                Menus menu = this.getMenus(parent);

                List<Menus> childs = new ArrayList<Menus>();
                //循环判断
                for(Permission entity:entitys){
                    Long pid = entity.getParentId();
                    if(pid != null && pid.equals(parent.getPid())){
                        childs.add(this.getMenus(entity));
                    }
                }

                menu.setChildren(childs);
                menus.add(menu);
            }

            return menus;
        }

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:获取菜单<br/>
     * @param entity
     * @return
     */
    private Menus  getMenus(Permission entity){
        Menus menu = new Menus();
        menu.setTitle(entity.getTitle());
        if(StringUtils.isEmpty(entity.getHref())){
            menu.setHref("");
        }else{
            menu.setHref(entity.getHref());
        }
        menu.setIcon(entity.getIcon());
        return menu;
    }
}
