package experiment;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author: ZH3FENG
 * @Date: 下午7:53 2017/12/11
 * @Modified By:
 * @Description: 我们想达到什么效果呢？通过修改序列化字节码，欺骗反序列化，
 * 
 * @see https://mp.weixin.qq.com/s/Daipik5qK6cIuYl49G-n4Q
 */
public class AccountWrapper implements Serializable {

    private static final long serialVersionUID = 200L;

    private void readObject(ObjectInputStream input) 
    throws Exception {
        input.defaultReadObject();
        
        try {
            input.readObject();//如果AccountWrapper没有实现writeObject，调用readObject会有问题，因为根据java序列化规范，readObject是负责读取自己writeObject写入的数据。
            					 //但是这里使用try catch包裹，已经捕获了异常，所以不会因为异常而终止。
            					//我们想通过修改序列化字节码（修改一个flag）来欺骗它--AccountWrapper实现了writeObject，从而继续读取下一个对象序列化数据
        } catch (Exception e) {
            System.out.println("WrapperClass.readObject: input.readObject error");
        }
       
    }
    
    /**
     * 为了便于修改序列化字节码，我们先看看正常实现了writeObject反序列化字节码是怎么样的
     */
//    private void writeObject(ObjectOutputStream output) 
//    	    throws Exception {
//    		 output.defaultWriteObject();
//    	     output.writeObject(new Account("zhangsan"));          
//    	}

}
