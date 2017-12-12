package experiment;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author: ZH3FENG
 * @Date: 下午9:52 2017/12/11
 * @Modified By:
 * @Description: 我们希望反序列化两个对象AccountWrapper和Account，先写AccountWrapper，再写Account，并且刻意使Account的passcode不是“root”（参加Account源码）。
 *               正常情况，无法反序列化出Account对象，因为在反序列化的过程中出现了异常。
 *               现在我们通过修改AccountWrapper的classDescFlags （SC_SERIALIZABLE->SC_WRITE_METHOD | SC_SERIALIZABLE)欺骗AccountWrapper，让其readObject方法读取Account对象的序列。
 * 
 * @see https://mp.weixin.qq.com/s/Daipik5qK6cIuYl49G-n4Q
 */
public class App {
	
	private final static String PATH = "/Users/re/TOOLs/pen/serialization/aaa.ser";
	
	public static void main(String[] args) {
		try {
			//serialize();
			deserialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private static void deserialize() throws ClassNotFoundException, IOException, NoSuchFieldException, SecurityException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(PATH)));
		in.readObject(); // 第一次，读出 Wrapper Class
		Object ob = in.readObject();// 第二次，读出 Account 对象
		System.out.println(ob);
	
		in.close();
	}
	/**
	 * 为了便于伪造序列化后的字节码，反序列化两次：
	 * 1）只序列化AccountWrapper，并且这时AccountWrapper实现了writeObject（此方法实现写入Account）
	 * 2）序列化AccountWrapper和Account，并且这时AccountWrapper没有实现了writeObject
	 * @throws IOException
	 */
	private static void serialize() throws IOException {
		
		//first
//		AccountWrapper accountWrapper = new AccountWrapper();
//		ObjectOutputStream obs = new ObjectOutputStream(new FileOutputStream(new File(PATH)));
//		obs.writeObject(accountWrapper);
//		obs.close();
		
		//second	
		AccountWrapper accountWrapper = new AccountWrapper();
	    Account account = new Account("zhangsan");
		
		ObjectOutputStream obs = new ObjectOutputStream(new FileOutputStream(new File(PATH)));
		obs.writeObject(accountWrapper);
		obs.writeObject(account);
		obs.close();
		
	}
	

}
