package 打卡;
	import java.io.EOFException;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
	import java.text.SimpleDateFormat;
	import java.util.ArrayList;
	import java.util.Date;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Scanner;

	public class Main {
		static Company c = new Company();

		/**
		 * 保存签到数据
		 * @param obj
		 */
		private static void saveData(Object obj) {
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try {
				//实例化上面定义的io流对象
				fos = new FileOutputStream("d:/io/dk.txt");
				oos = new ObjectOutputStream(fos);
				//使用处理流ObjectOutputStream的writeObject方法来进行序列化
				oos.writeObject(obj);
				//强制io流输出到文件
				oos.flush();
				
			} catch (FileNotFoundException ex) {
				System.out.println(ex.getMessage());
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
					if (oos != null) {
						oos.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		/**
		 *  签到
		 * @param id
		 */
		public static void qianDao(int id) {
			Employee e = c.findEmp(id);
			if (e != null) {
				// 获取系统当前时间
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = sdf.format(d);
				List<DakaInfo> list = c.map.get(strDate);
				if (list != null && list.isEmpty() == false) {
					for (DakaInfo dk : list) {
						if (dk.id == id) {
							System.out.println("今天已经打过卡了");
							return;
						}
					}
					DakaInfo dk = new DakaInfo();
					dk.id = e.getId();
					dk.qdTime = d;
					list.add(dk);
					c.map.put(strDate, list);
					
					//保存签到数据
					saveData(c.map);
				} else {
					DakaInfo dk = new DakaInfo();
					dk.id = id;
					dk.qdTime = d;
					list = new ArrayList<DakaInfo>();
					list.add(dk);
					c.map.put(strDate, list);
					
					//保存签到数据
					saveData( c.map);
				}
				System.out.println("工号： " + id + " 打卡成功");
			} else {
				System.out.println("无此ID员工");
			}
		}


		/**
		 * 签退
		 * @param id
		 */
		public static void qianTui(int id) {
			Employee e = c.findEmp(id);
			if (e != null) {
				// 获取系统当前时间
				Date d = new Date();
				String strDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
				List<DakaInfo> list = c.map.get(strDate);
				if (list != null && list.isEmpty() == false) {
					boolean flag = true;
					for (DakaInfo dk : list) {
						if(dk.id == id){
							dk.qtTime = d;
							flag = false;
							break;
						}
					}
					
					if(flag == false){
						//保存签退数据
						saveData(c.map);
						System.out.println("签退成功");
						
					}else{
						System.out.println("今天还没有签到，无法签退");
					}
				} else {
					System.out.println("今天还没有签到，无法签退");
				}
			} else {
				System.out.println("无此ID员工");
			}
		}

		/**
		 * 查看
		 */
		public static void showInfo() {
			Date d = new Date();
			String strDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
			List<DakaInfo> list = c.map.get(strDate);
//			if (list != null && list.isEmpty() == false) {
//				for (DakaInfo dk : list) {
//					int id = dk.id;
//					Employee e = c.findEmp(id);
//					System.out.println("工号为： " + e.getId() + " 姓名:" + e.getName());
//					System.out.println("签到时间为:"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(dk.qdTime));
//					try {
//						System.out.println("签退时间为:"
//								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//										.format(dk.qtTime));
//					} catch (NullPointerException e2) {
//						System.out.println("签退时间为：无记录");
//					}
//				}
//			} else {
//				System.out.println("今天暂时没有打卡信息");
//			}
			
			queryData();
		}

		/**
		 * 查询打卡数据
		 */
		private static void queryData() {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			StringBuilder sb = new StringBuilder();
				try {
					fis = new FileInputStream("d:/io/dk.txt");
					ois = new ObjectInputStream(fis);
					Object c = null;
					while ((c = ois.readObject()) != null) {
						Map<String,List<DakaInfo>> maps = (Map<String, List<DakaInfo>>) c;
						Date d = new Date();
						String strDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
						List<DakaInfo> list = maps.get(strDate);
						if (null != list  && list.isEmpty() == false) {
							for (DakaInfo dk : list) {
								System.out.println(dk.id);
								sb.append("员工号为："+dk.id+"\n");
								if(null != dk.qdTime) {
									sb.append("签到时间为："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
											.format(dk.qdTime)+"\n");
								}
								if(null != dk.qtTime) {
									sb.append("签退时间为："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
											.format(dk.qtTime)+"\n");
								}
							}
						} else {
							System.out.println("今天暂时没有打卡信息");
						}
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EOFException e) {
					// TODO Auto-generated catch block
				} 	
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
						try {
							if(ois !=null){
								ois.close();
							}
							if(fis !=null){
								fis.close();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				System.out.println(sb.toString());
		}

		// 主方法
		public static void main(String[] args) {
			c.addEmp(new Employee(1111, "齐一"));
			c.addEmp(new Employee(1112, "齐二"));
			c.addEmp(new Employee(1113, "齐三"));
			c.addEmp(new Employee(1114, "齐四"));
			c.addEmp(new Employee(1115, "齐五"));

			Scanner scan = new Scanner(System.in);
			//获取文件中所有打卡数据
			getAllRegisterData();
			
			while (true) {
				System.out.println("----员工打卡系统----");
				System.out.println("0--------退出");
				System.out.println("1--------签到");
				System.out.println("2--------签退");
				System.out.println("3--------查看签到信息");
				System.out.println("请输入想执行的操作：");
				int n = scan.nextInt();

				if (n == 0) {
					System.out.println("已退出");
					break;
				}

				switch (n) {
				case 1:
					System.out.println("请输入签到员工ID：");
					int id1 = scan.nextInt();
					qianDao(id1);
					break;
				case 2:
					System.out.println("请输入签退员工ID：");
					int id2 = scan.nextInt();
					qianTui(id2);
					break;
				case 3:
					showInfo();
					break;
				default:
					break;
				}
			}
		}

		/**
		 * 初始化方法，获取文件中所有打卡数据
		 */
		private static void getAllRegisterData() {
			Map<String,List<DakaInfo>> allMap = new HashMap<String,List<DakaInfo>>();
			
			FileInputStream fis = null;
			ObjectInputStream ois = null;
				try {
					fis = new FileInputStream("d:/io/dk.txt");
					ois = new ObjectInputStream(fis);
					Object obj = null;
					while ((obj = ois.readObject()) != null) {
						allMap.putAll((Map<String, List<DakaInfo>>) obj);
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EOFException e) {
					// TODO Auto-generated catch block
				} 	
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
						try {
							if(ois !=null){
								ois.close();
							}
							if(fis !=null){
								fis.close();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			c.map = allMap;
		}


	}

