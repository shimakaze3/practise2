package ��;
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
		 * ����ǩ������
		 * @param obj
		 */
		private static void saveData(Object obj) {
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try {
				//ʵ�������涨���io������
				fos = new FileOutputStream("d:/io/dk.txt");
				oos = new ObjectOutputStream(fos);
				//ʹ�ô�����ObjectOutputStream��writeObject�������������л�
				oos.writeObject(obj);
				//ǿ��io��������ļ�
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
		 *  ǩ��
		 * @param id
		 */
		public static void qianDao(int id) {
			Employee e = c.findEmp(id);
			if (e != null) {
				// ��ȡϵͳ��ǰʱ��
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = sdf.format(d);
				List<DakaInfo> list = c.map.get(strDate);
				if (list != null && list.isEmpty() == false) {
					for (DakaInfo dk : list) {
						if (dk.id == id) {
							System.out.println("�����Ѿ��������");
							return;
						}
					}
					DakaInfo dk = new DakaInfo();
					dk.id = e.getId();
					dk.qdTime = d;
					list.add(dk);
					c.map.put(strDate, list);
					
					//����ǩ������
					saveData(c.map);
				} else {
					DakaInfo dk = new DakaInfo();
					dk.id = id;
					dk.qdTime = d;
					list = new ArrayList<DakaInfo>();
					list.add(dk);
					c.map.put(strDate, list);
					
					//����ǩ������
					saveData( c.map);
				}
				System.out.println("���ţ� " + id + " �򿨳ɹ�");
			} else {
				System.out.println("�޴�IDԱ��");
			}
		}


		/**
		 * ǩ��
		 * @param id
		 */
		public static void qianTui(int id) {
			Employee e = c.findEmp(id);
			if (e != null) {
				// ��ȡϵͳ��ǰʱ��
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
						//����ǩ������
						saveData(c.map);
						System.out.println("ǩ�˳ɹ�");
						
					}else{
						System.out.println("���컹û��ǩ�����޷�ǩ��");
					}
				} else {
					System.out.println("���컹û��ǩ�����޷�ǩ��");
				}
			} else {
				System.out.println("�޴�IDԱ��");
			}
		}

		/**
		 * �鿴
		 */
		public static void showInfo() {
			Date d = new Date();
			String strDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
			List<DakaInfo> list = c.map.get(strDate);
//			if (list != null && list.isEmpty() == false) {
//				for (DakaInfo dk : list) {
//					int id = dk.id;
//					Employee e = c.findEmp(id);
//					System.out.println("����Ϊ�� " + e.getId() + " ����:" + e.getName());
//					System.out.println("ǩ��ʱ��Ϊ:"
//							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//									.format(dk.qdTime));
//					try {
//						System.out.println("ǩ��ʱ��Ϊ:"
//								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//										.format(dk.qtTime));
//					} catch (NullPointerException e2) {
//						System.out.println("ǩ��ʱ��Ϊ���޼�¼");
//					}
//				}
//			} else {
//				System.out.println("������ʱû�д���Ϣ");
//			}
			
			queryData();
		}

		/**
		 * ��ѯ������
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
								sb.append("Ա����Ϊ��"+dk.id+"\n");
								if(null != dk.qdTime) {
									sb.append("ǩ��ʱ��Ϊ��"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
											.format(dk.qdTime)+"\n");
								}
								if(null != dk.qtTime) {
									sb.append("ǩ��ʱ��Ϊ��"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
											.format(dk.qtTime)+"\n");
								}
							}
						} else {
							System.out.println("������ʱû�д���Ϣ");
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

		// ������
		public static void main(String[] args) {
			c.addEmp(new Employee(1111, "��һ"));
			c.addEmp(new Employee(1112, "���"));
			c.addEmp(new Employee(1113, "����"));
			c.addEmp(new Employee(1114, "����"));
			c.addEmp(new Employee(1115, "����"));

			Scanner scan = new Scanner(System.in);
			//��ȡ�ļ������д�����
			getAllRegisterData();
			
			while (true) {
				System.out.println("----Ա����ϵͳ----");
				System.out.println("0--------�˳�");
				System.out.println("1--------ǩ��");
				System.out.println("2--------ǩ��");
				System.out.println("3--------�鿴ǩ����Ϣ");
				System.out.println("��������ִ�еĲ�����");
				int n = scan.nextInt();

				if (n == 0) {
					System.out.println("���˳�");
					break;
				}

				switch (n) {
				case 1:
					System.out.println("������ǩ��Ա��ID��");
					int id1 = scan.nextInt();
					qianDao(id1);
					break;
				case 2:
					System.out.println("������ǩ��Ա��ID��");
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
		 * ��ʼ����������ȡ�ļ������д�����
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

