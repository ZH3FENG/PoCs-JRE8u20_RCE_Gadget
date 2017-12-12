package experiment;

import java.io.ObjectInputStream;
import java.io.Serializable;


/**
 * @author: ZH3FENG
 * @Date: 下午7:53 2017/12/11
 * @Modified By:
 * @Description: 模拟序列化过程中出现异常，Account实现了自己的readObject方法，对于序列化的对象要求其passcode值为"root"，
 *               所以只要我们序列化的实例passcode值不为"root",就会出现异常
 * @see https://mp.weixin.qq.com/s/Daipik5qK6cIuYl49G-n4Q
 */
public class Account implements Serializable{
	
	private static final long serialVersionUID = 100L;
    private String passcode;
    
    public Account(String passcode) {
        this.passcode = passcode;
    }
    
    private void readObject(ObjectInputStream input) 
    throws Exception {
        input.defaultReadObject();
        
        /*这里有意制造异常*/
        if (!this.passcode.equals("root")) {
            throw new Exception("passcode is not correct");
        }
    }
    
}
