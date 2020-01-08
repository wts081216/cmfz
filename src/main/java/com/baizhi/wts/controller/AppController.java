package com.baizhi.wts.controller;

import com.baizhi.wts.dao.GuruAndUserDao;
import com.baizhi.wts.entity.*;
import com.baizhi.wts.service.*;
import com.baizhi.wts.util.Smsutil;
import org.elasticsearch.cluster.metadata.AliasAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.persistence.Id;
import javax.print.DocFlavor;
import java.security.Signature;
import java.util.*;

@RestController
@RequestMapping("/AppController")
public class AppController {
    HashMap hashMap = new HashMap();
    Jedis jedis = new Jedis("192.168.181.15", 7000);
    @Autowired
    UserService userService;
    @Autowired
    BannerService bannerService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    AlbumService albumService;
    @Autowired
    CourseService courseService;
    @Autowired
    CounterService counterService;
    @Autowired
    GuruService guruService;
    @Autowired
    GuruAndUserDao guruAndUserDao;
    //1.登录
    @RequestMapping("/login")
    public Map login(String phone, String password) {
        Map map = userService.queryByPhone(phone, password);
        return map;
    }

    //2.发送验证码
    @RequestMapping("/sendCode")
    public Map sendCode(String phone) {
        String s = UUID.randomUUID().toString();
        String code = s.substring(0, 3);//1234
        try {
            Smsutil.sendSms(phone, code);//13137132561    1234
            jedis.set(phone, code);
            jedis.expire(phone, 180);//13137132561    1234   180s
            hashMap.put("status", "200");
            hashMap.put("message", "短信发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("message", "短信发送失败");
        }
        return hashMap;
    }

    //3. 注册接口
    @RequestMapping("/regist")
    public Map regist(String clientCode, User user) {
        try {
            String severCode = jedis.get(user.getPhone());
            //先判断验证码是否正确
            if (severCode != null) {
                //验证码不为空
                if (clientCode.equals(severCode)) {
                    //验证码正确  可以注册
                    String uuid = UUID.randomUUID().toString();
                    user.setId(uuid);
                    user.setSalt(uuid);
                    user.setStatus("1");
                    userService.add(user);
                    hashMap.put("status", "200");
                    hashMap.put("message", "注册成功");
                } else {
                    hashMap.put("status", "-200");
                    hashMap.put("message", "验证码输入错误");
                }
            } else {
                //验证码为空
                hashMap.put("status", "-200");
                hashMap.put("message", "验证码失效");
            }
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
            hashMap.put("message", "注册失败");
        }
        return hashMap;
    }
    //4. 补充个人信息接口
   @RequestMapping("/updateInformation")
    public Map updateInformation(User user){
        try {
            userService.update(user);
            //User user1 = new User(user.getId(), null,user.getPassword(), null, null,user.getPhoto(),user.getName(),user.getNickName(),user.getSex(),user.getSign(),user.getLocation(), new Date(),null);
            User user2 = userService.queryOne(user);
            hashMap.put("status","200");
            hashMap.put("user",user2);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","添加失败");
        }
        return  hashMap;
    }
    //5. 一级页面展示接口
    @RequestMapping("/queryOnePage")
    public  Map queryOnePage(String uid,String type,String sub_type){
        try{
            if(type.equals("all")){
                List<Banner> banners = bannerService.queryBannerBytime();
                List<Article> articles = articleService.queryAll();
                Map map = albumService.quryBypage(0, 5);
                hashMap.put("status","200");
                hashMap.put("head",banners);
                hashMap.put("albums",map);
                hashMap.put("articles",articles);
            }else if(type.equals("wen")) {
                Map map = albumService.quryBypage(0, 5);
                hashMap.put("status","200");
                hashMap.put("albums",map);
            }else {
                    if(sub_type.equals("ssyj")){
                        List<Article> articles = articleService.queryAll();
                        hashMap.put("status","200");
                        hashMap.put("articles",articles);
                    }else{
                        List<Article> articles = articleService.queryAll();
                        hashMap.put("status","200");
                        hashMap.put("articles",articles);
                    }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //6. 文章详情接口
    @RequestMapping("/articleInterfaceDetail")
    public Map articleInterfaceDetail(String uid,String id){
        List<Article> articles = articleService.queryAll();
        hashMap.put("status","200");
        hashMap.put("articles",articles);
        return hashMap;
    }
    //7. 专辑详情接口
    @RequestMapping("/albumInterfaceDetail")
    public Map albumInterfaceDetail(String uid,String id){
        List<Album> albums = albumService.queryAll();
        hashMap.put("status","200");
        hashMap.put("albums",albums);
        return hashMap;
    }
    //8. 展示功课
    @RequestMapping("/queryCourse")
    public Map queryCourse(String uid){
        try{
            List<Course> courses = courseService.queryById(uid);
            hashMap.put("status","200");
            hashMap.put("option",courses);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //9. 添加功课
    @RequestMapping("/insertCourse")
    public Map insertCourse(String uid,String title){
        try{
            Course course = new Course();
            course.setId(uid);
            course.setTitle(title);
            courseService.add(course);
            hashMap.put("status","200");
            hashMap.put("option",course);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //10. 删除功课
    @RequestMapping("/deleteCourse")
    public Map deleteCourse(String uid,String id){
        try{
            Course course = new Course();
            course.setId(id);
            courseService.delete(Arrays.asList(id));
            hashMap.put("status","200");
            hashMap.put("option",course);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //11. 展示计数器
    @RequestMapping("/queryCounter")
    public Map queryCounter(String uid,String id){
        try{
            List<Counter> counters = counterService.queryById(id);
            hashMap.put("status","200");
            hashMap.put("counters",counters);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //12. 添加计数器
    @RequestMapping("/insertCounter")
    public Map insertCounter(String uid,String title){
        try{
            Counter counter = new Counter();
            counter.setId(uid);
            counter.setTitle(title);
            counterService.add(counter);
            hashMap.put("status","200");
            hashMap.put("counters",counter);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //13. 删除计数器
    @RequestMapping("/deleteCounter")
    public Map deleteCounter(String uid,String id){
        try{
            Counter counter = new Counter();
            counter.setId(id);
            counterService.delete(Arrays.asList(id));
            hashMap.put("status","200");
            hashMap.put("message","删除成功");
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","删除失败");
        }
        return hashMap;
    }
    //14. 表更计数器
    @RequestMapping("/updateCounter")
    public Map updateCounter(String uid,String id,Integer count){
        try{
            Counter counter = new Counter();
            counter.setCount(count);
            counter.setId(id);
            counter.setUserId(uid);
            counterService.update(counter);
            counterService.queryById(counter.getId());
            hashMap.put("status","200");
            hashMap.put("counters",counter);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //15. 修改个人信息
    @RequestMapping("/updateUser")
    public Map updateUser(User user){
        try{
            User user1 = new User(user.getId(),user.getPhone(),user.getPassword(),null,"1",user.getPhoto(),user.getName(),user.getNickName(),user.getSex(),user.getSign(),user.getLocation(),null,null);
            user.setSalt(UUID.randomUUID().toString());
            userService.update(user1);
            User user2 = userService.queryOne(user1);

            hashMap.put("status","200");
            hashMap.put("user",user2);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //16. 金刚道友
    @RequestMapping("/randomFriends")
    public List<User> randomFriends(String uid){
        List<User> users = userService.queryRandom(uid);
        return  users;
    }
    //17. 展示上师列表
    @RequestMapping("/queryGuru")
    public Map queryGuru(String uid){
        try{
            List<Guru> gurus = guruService.quryAll();
            hashMap.put("status","200");
            hashMap.put("user",gurus);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
   //18. 添加关注上师
    @RequestMapping("/addUser")
    public Map addUser(String uid,String id){
        try{
            Guruanduser guruanduser = new Guruanduser();
            guruanduser.setGuruId(id);
            guruanduser.setUid(uid);
            String uuid = UUID.randomUUID().toString();
            guruanduser.setId(uuid);
            guruAndUserDao.insert(guruanduser);
            hashMap.put("status","200");
            hashMap.put("guruanduser",guruanduser);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
}
