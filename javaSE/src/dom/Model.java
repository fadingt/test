package dom;

interface MouseListener{
	void click();
}
/**
 * 监听者模式
 * @author liuxingyu
 *
 */
public class Model {
	private MouseListener[] listeners = new MouseListener[10];
	private int index = 0;
	public void addListener(MouseListener mouseListener){
		listeners[index++] = mouseListener;
	}
	
	public void click(){
		for (int i = 0; i < index; i++) {
			listeners[i].click();
		}
	}
	public static void main(String[] args) {
		Model model = new Model();
		model.addListener(new MouseListener(){
			public void click(){
				System.out.println("click1");
			}
		});
//		model.click();
		model.addListener(new MouseListener(){
			public void click(){
				System.out.println("click2");
			}
		});
		model.addListener(new MouseListener(){
			public void click(){
				System.out.println("click3");
			}
		});
		model.click();
	}

}
