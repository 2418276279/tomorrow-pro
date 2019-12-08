package com.bw.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

/** 类说明:此工具类用于操作文件及文件夹的相关操作  复制 删除等处理
 * 功能:	
 * 1,建立目的目录。 
 * 2，遍历源目录。 
 * 3，遍历过程中，创建文件或者文件夹。 原理：其实就是改变了源文件或者目录的目录头。
 * 4,copy目录。通过源目录在目的目录创建新目录。 
 * 5,copy文件
 * 6,替换路径。
 * 7,copy目录。通过源目录在目的目录创建新目录。 
 * 8,文件/目录 部分处理
 * 		1):获取文件的后缀名并转化成大写
 * 		2):创建多级目录
 * 		3):删除文件/目录(递归删除文件/目录)
 * 		4):文件/目录 重命名
 * 		5): 新建目录,更新目录 删除目录
 * 		6):文件/目录 重命名
 * 		7):文件/目录 重命名
 * 			
 * 
 * 
 * @datetime 2019-12-06
 */

//FileRelatedOperation
public class FileRelatedOperation {
	private File sDir, dDir, newDir;

	public FileRelatedOperation(String s, String d) {
		this(new File(s), new File(d));
	}

	FileRelatedOperation(File sDir, File dDir)// c:\\Test d:\\abc
	{
		this.sDir = sDir;
		this.dDir = dDir;
	}

	public void FileRelatedOperation() throws IOException {
		// 是创建目的目录。也就是创建要拷贝的源文件夹。Test
		// 获取源文件夹名称。
		String name = sDir.getName();
		// 通过该名称在目的目录创建该文件夹，为了存放源文件夹中的文件或者文件夹。
		// 将目的目录和源文件夹名称，封装成File对象。
		newDir = dDir;
		// new File(dDir,name);
		// 调用该对象的mkdir方法。在目的目录创建该文件夹。d:\\abc\\Test
		newDir.mkdir();//

		// 遍历源文件夹。
		listAll(sDir);
	}

	/*
	 * 将遍历目录封装成方法。 在遍历过程中，遇到文件创建文件。 遇到目录创建目录。
	 */
	private void listAll(File dir) throws IOException {
		File[] files = dir.listFiles();
		for (int x = 0; x < files.length; x++) {
			if (files[x].isDirectory()) {
				createDir(files[x]);// 调用创建目录的方法。
				listAll(files[x]);// 在继续进行递归。进入子级目录。
			} else {
				createFile(files[x]);// 调用创建文件的方法。
			}
		}
	}

	/*
	 * copy目录。通过源目录在目的目录创建新目录。
	 */
	private void createDir(File dir) {
		File d = replaceFile(dir);
		d.mkdir();
	}

	/*
	 * copy文件。
	 */
	private void createFile(File file) throws IOException {
		File newFile = replaceFile(file);
		// copy文件是一个数据数据传输的过程。需要通过流来完成。
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(newFile);
		byte[] buf = new byte[1024 * 2];
		int num = 0;
		while ((num = fis.read(buf)) != -1) {
			fos.write(buf, 0, num);
		}
		fos.close();
		fis.close();
	}

	/*
	 * 替换路径。
	 */
	private File replaceFile(File f) {
		// 原理是：将源目录的父目录(C:\\Tset)，替换成目的父目录。（d:\\abc\\Test）
		String path = f.getAbsolutePath();// 获取源文件或者文件夹的决定路径。
		// 将源文件或者文件夹的绝对路径替换成目的路径。
		String newPath = path.replace(sDir.getAbsolutePath(),
				newDir.getAbsolutePath());
		// 将新的目的路径封装成File对象
		File newFile = new File(newPath);
		return newFile;
	}
	// =============================================文件/目录
	// 部分处理=============================================================================
	/**
	 * 文件/目录 部分处理
	 * 
	 * @createTime 2019-12-06
	 * @version 1.0
	 */

	/**
	 * 获取文件的后缀名并转化成大写
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public String getFileSuffix(String fileName) throws Exception {
		return fileName
				.substring(fileName.lastIndexOf(".") + 1, fileName.length())
				.toUpperCase();
	}

	/**
	 * 创建多级目录
	 * 
	 * @param path
	 *            目录的绝对路径
	 */
	public void createMultilevelDir(String path) {
		try {
			StringTokenizer st = new StringTokenizer(path, "/");
			String path1 = st.nextToken() + "/";
			String path2 = path1;
			while (st.hasMoreTokens()) {

				path1 = st.nextToken() + "/";
				path2 += path1;
				File inbox = new File(path2);
				if (!inbox.exists())
					inbox.mkdir();

			}
		} catch (Exception e) {
			System.out.println("目录创建失败" + e);
			e.printStackTrace();
		}

	}

	/**
	 * 删除文件/目录(递归删除文件/目录)
	 * 
	 * @param path
	 *            文件或文件夹的绝对路径
	 */
	public void deleteAll(String dirpath) {
		if (dirpath == null) {
			System.out.println("目录为空");
		} else {
			File path = new File(dirpath);
			try {
				if (!path.exists())
					return;// 目录不存在退出
				if (path.isFile()) // 如果是文件删除
				{
					path.delete();
					return;
				}
				File[] files = path.listFiles();// 如果目录中有文件递归删除文件
				for (int i = 0; i < files.length; i++) {
					deleteAll(files[i].getAbsolutePath());
				}
				path.delete();

			} catch (Exception e) {
				System.out.println("文件/目录 删除失败" + e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 文件/目录 重命名
	 * 
	 * @param oldPath
	 *            原有路径（绝对路径）
	 * @param newPath
	 *            更新路径
	 * @author  注：不能修改上层次的目录
	 */
	public void renameDir(String oldPath, String newPath) {
		File oldFile = new File(oldPath);// 文件或目录
		File newFile = new File(newPath);// 文件或目录
		try {
			boolean success = oldFile.renameTo(newFile);// 重命名
			if (!success) {
				System.out.println("重命名失败");
			} else {
				System.out.println("重命名成功");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}

	// =============================================目录处理=============================================================================

	/**
	 * 新建目录
	 */
	public static boolean newDir(String path) throws Exception {
		File file = new File(path);
		return file.mkdirs();// 创建目录
	}

	/**
	 * 删除目录
	 */
	public static boolean deleteDir(String path) throws Exception {
		File file = new File(path);
		if (!file.exists())
			return false;// 目录不存在退出
		if (file.isFile()) // 如果是文件删除
		{
			file.delete();
			return false;
		}
		File[] files = file.listFiles();// 如果目录中有文件递归删除文件
		for (int i = 0; i < files.length; i++) {
			deleteDir(files[i].getAbsolutePath());
		}
		file.delete();

		return file.delete();// 删除目录
	}

	/**
	 * 更新目录
	 */
	public static boolean updateDir(String path, String newPath)
			throws Exception {
		File file = new File(path);
		File newFile = new File(newPath);
		return file.renameTo(newFile);
	}

	public static void main(String d[]) throws Exception {
		// deleteDir("d:/ff/dddf");
		updateDir(
				"D:\\TOOLS\\Tomcat 6.0\\webapps\\BCCCSM\\nationalExperiment/22222",
				"D:\\TOOLS\\Tomcat 6.0\\webapps\\BCCCSM\\nationalExperiment/224222");
	}

	// =====================================删除文件夹==============================================================

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

}