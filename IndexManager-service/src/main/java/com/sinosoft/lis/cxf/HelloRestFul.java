package com.sinosoft.lis.cxf;

import com.sinosoft.lis.emtry.User;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Component("HelloRestFul")
//一般采用子类集成 实现 此处不做讨论
@Path("/hello")
public class HelloRestFul {

    @Autowired
    private JdbcTemplate jdbcTemplate;

   //获取
    @GET
    @Path("/user/{id}/")
    @Produces({"application/json"})
    public User getUser(@PathParam("id") String id){
        String sql = "SELECT id, name, pass FROM users WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{ id }, new BeanPropertyRowMapper<User>(User.class));
        return user;
    }


    //获取
    @GET
    @Path("/pass")
    @Produces({"application/json"}) //@QueryParam("id") 注解使用时 path 应该为/pass , 而不是/pass/
    public String getPass(@QueryParam("id") String id){
        String sql = "SELECT pass FROM users WHERE id = ?";
        String pass = jdbcTemplate.queryForObject(sql, String.class, id);
        return pass;
    }

    //更新
    @PUT //请求方式 put 更新, post 添加, delete 删除, get 查询
    @Path("/user/") //路径
    @Produces({"application/xml"})//返回的格式, 可以是多个, 默认*/* ,详细参见MIME
    @Consumes({"application/json"})//允许接收的格式, 可以是多个, 默认是*/*
    public Response updateBook(User user){
        Response r;
      // r = Response.noContent().build();

       r = Response.ok(true, MediaType.TEXT_PLAIN).build();
        return r;
    }

    //添加
    @POST
    @Path("/user/")
    @Consumes("application/json")
    ///@Produces({"application/xml"})
    public Response addBook(User user){
        Response r;
            r = Response.notModified().build();
            r = Response.ok(true, MediaType.TEXT_PLAIN).build();
        return r;
    }

    //添加
    @POST
    @Path("/user/upfile")
    @Consumes("multipart/form-data")
    @Produces({"application/xml"})
    public Response upfile(@Multipart(value = "file") Attachment attachment, @Multipart(value = "name") String name) throws IOException {
        DataHandler dh = attachment.getDataHandler();
        String fileName = dh.getName();
        InputStream in = dh.getInputStream();
        File file = new File("C:/Users/HP/Desktop/笔记--/" + fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
        in.close();

        Response r;
        r = Response.notModified().build();
        r = Response.ok(true, MediaType.TEXT_PLAIN).build();
        return r;
    }

   //删除
    @DELETE
    @Path("/user/{id}/")
    @Produces({"application/xml"})
    public Response deleteBook(@PathParam("id") String id){
        Response r;
        r = Response.notModified("id不存在").build();
        return r;
    }
}
