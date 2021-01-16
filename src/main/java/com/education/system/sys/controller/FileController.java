package com.education.system.sys.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.education.system.common.entity.Result;
/**
 * 文件删除的控制类
 * @author Dell
 *
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseContorller{

	/**
	 * 设置保存图片的虚拟路径
	 */
	//这里采用相对路径,毕竟上传到服务器之后这个文件是在Tomcat所在计算机中,所以以项目名为根路径为好
	
	private final static String saveFilePath = "D:\\eclipse\\soft\\eclipse\\codecache\\ssm_education_management_system\\src\\main\\webapp\\uploadImg\\";    

	private final static String upload_SuccessMsg = "图片上传成功！";
	private final static String upload_ErrorMsg = "图片信息不合法！";
	
	/**
	 * 单图片上传
	 * 
	 * @param myfile
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile myfile) throws IllegalStateException, IOException{
		//获取文件上传原名
		String oldFileName = myfile.getOriginalFilename();
		
		Map<String, Object> map = null;
		// 上传图片,判断图片名是否为空
		if (oldFileName != null && myfile !=null && oldFileName.length() > 0) {
			//文件合法
			//给文件一个新的名称,一个唯一标识,以防在上传同一个文件的时候存在同名文件导致上传失败.
			String newFileName = UUID.randomUUID()+oldFileName.substring(oldFileName.indexOf("."));
			//新图片
			File newFile = new File(saveFilePath + newFileName);
			// 将内存中的数据写入磁盘
			myfile.transferTo(newFile);
			// 将新图片名称返回到前端
			map = new HashMap<String, Object>();
			map.put("success", upload_SuccessMsg);
			map.put("url", newFileName);
			
		}else {
			
			map = new HashMap<String, Object>();
			map.put("error", upload_ErrorMsg);
		}
		
		
		return map;
		
		
	}
	
	/**
	 * 多文件上传
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public Map<String, Object> springUpload(@RequestParam(value = "file") CommonsMultipartFile[] files, HttpServletRequest request){
		Map<String, Object> map = null;
		
		// 判断文件夹是否存在
			File file = new File(saveFilePath);
			if (!file.isDirectory()) {
				// 创建文件夹
				file.mkdirs();
			}
			
			//上传图片
			if (files != null && files.length > 0) {
				//批量上传
				for (int i = 0; i < files.length; i++) {
					try {
						
						//获取上传图片原名
						String oldFileName = files[i].getOriginalFilename();
						//将名称使用唯一标识符进行修葺
						String newFileName = UUID.randomUUID()+oldFileName.substring(oldFileName.indexOf("."));
						
						//新的图片
						File newFile = new File(saveFilePath+newFileName);
						//将内存中的数据(目标文件)写入磁盘
						files[i].transferTo(newFile);
						// 将新图片名称返回到前端
						map = new HashMap<String, Object>();
						map.put("success", upload_SuccessMsg);
						map.put("url", newFileName);
					} catch (IllegalStateException e) {

						e.printStackTrace();
						map.put("error", "无效状态异常");
						
					}catch (IOException e) {
						e.printStackTrace();
						map.put("error","文件读写异常");
					}
				}
				
				
			}else {
				map = new HashMap<String, Object>();
				map.put("error", upload_ErrorMsg);			
			}
		
		return map;
		
	}
	
	@RequestMapping(value="/download",method =RequestMethod.GET)
	public Result download(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException{
		//创建一个Result对象
		Result result = new Result();
		//下载文件,下载文件要知道文件的路径,以及文件名,这些从前端传过来.
		//判断文件名是否为空
		if (Objects.isNull(fileName)) {
			//文件名为空,将提示信息传回前端页面
			result.setMsg("请选择下载的图片");
			result.setSuccess(false);
			return result;
		}
		
		File file = new File(saveFilePath+fileName);
		//判断该文件是否存在
		if (!file.exists()) {
			result.setMsg("文件不存在");
			result.setSuccess(false);
			return result;
		}
		
		//如果文件存在,就要将文件以流的形式传出去.
		byte[] bytes = new byte[1024];
		
		BufferedInputStream bufferedInputStream = null;
		
		OutputStream outputStream = null;
		
		FileInputStream  fileInputStream = null;
		
		try {
			//将文件放入文件输入流
			fileInputStream = new FileInputStream(file);
			//将文件输入流放入缓冲流中,为了提高传输效率
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			//响应给浏览器,并通知其,该为流
			response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
			// 设置下载的文件的名称-该方式已解决中文乱码问题，postman可以，swagger看到的是%...等，浏览器直接输url,OK
			response.setHeader("Content-Disposition","attchment;filename="+fileName+";filename*=utf-8''"+ URLEncoder.encode(fileName, "UTF-8"));
			
			//获取输出流
			outputStream = response.getOutputStream();
			
			int length;
			while((length = bufferedInputStream.read(bytes))!=-1) {
				//在缓冲字节数组中,只要还有内容就要将流进行循环输出
				//直接响应流
				outputStream.write(bytes, 0, length);
				
			}
			//刷新缓冲区
			outputStream.flush();
			
		} catch (Exception e) {
			//如果出现异常就打印异常信息
			e.printStackTrace();
		}finally {
			//释放资源
			try {
				//先开后关
				if (outputStream != null) {
					outputStream.close();
				}
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
				if (fileInputStream != null) {
					fileInputStream.close();
				}
				return null;
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
		//文件上传成功
		result.setMsg("文件下载成功");
		result.setSuccess(true);
		return result;
	}
	
	
}
