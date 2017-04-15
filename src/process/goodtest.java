package process;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import javax.swing.JScrollPane;
import javax.swing.JLabel;


public class goodtest extends JFrame {

	static JPanel contentPane;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();
	static JTextField pcb_teminate;
	public static JTextField pcb_create;
	static JTextField pcb_running;
	static JTextField txtSystem;
	static JTextField runningtime;
	static List<PCB> pcbs;
	static List<PCB> readypcbs;
	static List<PCB> blockpcbs;
	
	static int allTime=0;
	static boolean IOstatus = false;
	static int time_piece = 4;
	static PCB currentpcb;
	static JButton Startbutton;
	static JTable table_pcbready;
	static JTable table_pcbblock;
	static PCB currentcreatepcb = null;
	static JLabel lblIoState;
	static JLabel lblNewLabel_1;
	static JLabel lblNewLabel_2;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//符合 windows系统的风格控件
		//try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        //}catch(Exception e){}  
		
		System.out.println("hello");
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					goodtest frame = new goodtest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//从json文件导入进程信息
		String processMessage=loadProcess();
		pcbs=new ArrayList<>();
		JSONArray jsonArray=JSON.parseArray(processMessage);
		pcbs=JSONArray2ObjectList(jsonArray);
		
		
	}

	/**
	 * Create the frame.
	 */
	public goodtest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300,100,900,620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel leftBox = new JPanel();
		contentPane.add(leftBox, BorderLayout.WEST);
		leftBox.setLayout(new BoxLayout(leftBox, BoxLayout.Y_AXIS));
		
		JPanel PCB_Create = new JPanel();
		PCB_Create.setBorder(new TitledBorder(null, "PCB_Create", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftBox.add(PCB_Create);
		PCB_Create.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pcb_create = new JTextField();
		pcb_create.setFont(new Font("宋体", Font.PLAIN, 16));
		pcb_create.setEditable(false);
		pcb_create.setColumns(25);
		PCB_Create.add(pcb_create);
		
		JScrollPane PCB_Ready = new JScrollPane();
		PCB_Ready.setBorder(new TitledBorder(null, "PCB_Ready", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftBox.add(PCB_Ready);
		
		
		JTableOperation jTableInit = new JTableOperation();
		table_pcbready = jTableInit.JtableDataInit();
		GlobalObject.setjScrollPane(PCB_Ready);
		GlobalObject.setjTable(table_pcbready);
		PCB_Ready.setViewportView(table_pcbready);
		
		
		
		GlobalObject.setjScrollPane(PCB_Ready);
		GlobalObject.setjTable(table_pcbready);
		
		JPanel centerBox = new JPanel();
		contentPane.add(centerBox, BorderLayout.CENTER);
		centerBox.setLayout(new BoxLayout(centerBox, BoxLayout.Y_AXIS));
		
		JPanel PCB_Running = new JPanel();
		PCB_Running.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "PCB_Running", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		centerBox.add(PCB_Running);
		PCB_Running.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pcb_running = new JTextField();
		pcb_running.setFont(new Font("宋体", Font.PLAIN, 16));
		pcb_running.setEditable(false);
		pcb_running.setColumns(25);
		
		PCB_Running.add(pcb_running);
		
		JScrollPane PCB_Block = new JScrollPane();
		PCB_Block.setBorder(new TitledBorder(null, "PCB_Block", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centerBox.add(PCB_Block);
		
		table_pcbblock = jTableInit.JtableDataInit();
		
		PCB_Block.setViewportView(table_pcbblock);
		
		GlobalObject.setjScrollPane2(PCB_Block);
		GlobalObject.setjTable2(table_pcbblock);
		
		JPanel rightBox = new JPanel();
		contentPane.add(rightBox, BorderLayout.EAST);
		rightBox.setLayout(new BoxLayout(rightBox, BoxLayout.Y_AXIS));
		
		JPanel PCB_Terminate_panel = new JPanel();
		PCB_Terminate_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "PCB_Terminate", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		rightBox.add(PCB_Terminate_panel);
		PCB_Terminate_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pcb_teminate = new JTextField();
		pcb_teminate.setFont(new Font("宋体", Font.PLAIN, 16));
		pcb_teminate.setEditable(false);
		PCB_Terminate_panel.add(pcb_teminate);
		pcb_teminate.setColumns(25);
		
		JPanel CPU_info_panel = new JPanel();
		CPU_info_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "CPU infomation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		rightBox.add(CPU_info_panel);
		CPU_info_panel.setLayout(new BoxLayout(CPU_info_panel, BoxLayout.Y_AXIS));
		
		JPanel panel_6 = new JPanel();
		CPU_info_panel.add(panel_6);
		
		JLabel lblNewLabel = new JLabel("CPU time piece :4s");
		panel_6.add(lblNewLabel);
		
		JPanel Block_panel = new JPanel();
		Block_panel.setBorder(new TitledBorder(null, "Block", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Block_panel.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent e) {
			}
		});
		
		JPanel start_panel = new JPanel();
		start_panel.setBorder(new TitledBorder(null, "Start", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		rightBox.add(start_panel);
		start_panel.setLayout(new BoxLayout(start_panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		start_panel.add(panel_1);
		
		Startbutton = new JButton("Start");
		panel_1.add(Startbutton);
		Startbutton.setFont(new Font("宋体", Font.PLAIN, 30));
		
		JPanel panel_2 = new JPanel();
		start_panel.add(panel_2);
		
		txtSystem = new JTextField();
		txtSystem.setEditable(false);
		txtSystem.setEnabled(false);
		txtSystem.setText("CPU Running Time :");
		txtSystem.setBorder(null);
		panel_2.add(txtSystem);
		txtSystem.setColumns(30);
		
		runningtime = new JTextField();
		runningtime.setEnabled(false);
		runningtime.setEditable(false);
		panel_2.add(runningtime);
		runningtime.setColumns(10);
		String s_alltime = String.valueOf(allTime);
		runningtime.setText(s_alltime);
		Startbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Startbutton.setEnabled(false);
				startbutton();
			}
		});
		rightBox.add(Block_panel);
		Block_panel.setLayout(new BoxLayout(Block_panel, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		Block_panel.add(panel);
		
		JButton btnNewButton = new JButton("Block");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blockbutton();
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("I/O Start");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IOstartbutton();
			}
		});
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("I/O Stop");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IOstopbutton();
			}
		});
		panel.add(btnNewButton_2);
		
		JPanel panel_3 = new JPanel();
		Block_panel.add(panel_3);
		
		lblIoState = new JLabel("I/O status : ");
		panel_3.add(lblIoState);
		
		lblNewLabel_2 = new JLabel("false");
		panel_3.add(lblNewLabel_2);
		
		JPanel panel_4 = new JPanel();
		Block_panel.add(panel_4);
		
		JLabel lblIoTime = new JLabel("I/O time for each blocked process : 3s");
		panel_4.add(lblIoTime);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane, rightBox}));
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}

	class JTableOperation {
	    public JTable JtableDataInit(){
	        //goodtest goodtestw = new goodtest();
	       // List<PCB> pcbs = goodtestw.getpcbsdata();

	        Object[][] tablepcbs = new String[20][3];
	        //遍历List
	        for (int i = 0; i < 8; i++) {
	        	tablepcbs[i][0] = "";
	        	tablepcbs[i][1] = "";
	        	tablepcbs[i][2] = "";
	        }
	        String[] Names={"Name","Runtime","status"};
	        table_pcbready =new JTable(tablepcbs,Names);
	        //table.setFont(new Font("Dialog",1,12));
	        return table_pcbready;
	    }
	}
	
	/**
	 * 主要函数
	 * @param jsonArray
	 * @return
	 */
	
    public static void reloadJTable(JTable table,List<PCB> pcbs,int num){
    	String[] Names={"Name","Runtime","Status"};
        String[][] tablepcbs = new String[20][3];
        //System.out.println("就绪队列的pcb数目" + readypcbs.size());
        //遍历List
        for (int i = 0; i < pcbs.size(); i++) {
        	tablepcbs[i][0] = pcbs.get(i).getName();
        	tablepcbs[i][1] = "" + pcbs.get(i).getRunTime();
        	tablepcbs[i][2] = "" + pcbs.get(i).getStatus();
        }

        table=new JTable(tablepcbs,Names);
        table.setFont(new Font("Dialog",1,12));
        if(num ==1){
        	GlobalObject.setjTable(table);
            GlobalObject.getjScrollPane().setViewportView(table);	
        }else{
        	GlobalObject.setjTable2(table);
            GlobalObject.getjScrollPane2().setViewportView(table);	
        }
       
    }
    
	
	public void blockbutton(){
		if(currentpcb!=null){
			blockpcbs.add(currentpcb);
			currentpcb.setStatus(2);
			System.out.println("阻塞" + currentpcb.getName() + "status 是" + currentpcb.getStatus());	
		}
	}
	
	public void IOstartbutton(){
		IOstatus = true;
		lblNewLabel_2.setText("true");
	}
	public void IOstopbutton(){
		IOstatus = false;
		lblNewLabel_2.setText("false");
	}
	
	public void startbutton(){//开始按钮
		System.out.println("startbutton start!");
		Startbutton.setEnabled(false);
		changeByTime(pcbs);
	}
	
	public static void changeByTime(List<PCB> pcbs) {
		
		blockpcbs = new ArrayList<>();
		readypcbs = new ArrayList<>();
		new Thread(new Runnable() {//刷新ready和blcok表格和其他文本组件的数据		
			@Override
			public void run() {
				while(true){
					if(currentcreatepcb!= null){
						pcb_create.setText(currentcreatepcb.getName());
					}else{
						pcb_create.setText("");
					}
					//System.out.println("刷新就绪队列");
					reloadJTable(GlobalObject.getjTable(),readypcbs,1);
					reloadJTable(GlobalObject.getjTable2(),blockpcbs,2);
					
					if(currentpcb!=null){
						pcb_running.setText(currentpcb.getName());//在pcb_running中显示运行的进程	
					}else{
						pcb_running.setText("");
					}
					

					runningtime.setText(String.valueOf(allTime));
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}					
				}
			}
		}).start();

		new Thread(new Runnable() {//检测I/O设备是否可用，如果可用，定时将阻塞队列插入到就绪队列中
			@Override
			public void run() {
				PCB pcb;
				while(true){
					System.out.println("检测IO设备");
					if(IOstatus == true && blockpcbs.size()!=0){
						pcb = blockpcbs.get(0);
						pcb.setStatus(0);
						readypcbs.add(pcb);
						blockpcbs.remove(pcb);
						try {
							Thread.sleep(3000);//每个进程所需的I/O时间为3s
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
		new Thread(new Runnable() {//创建PCB线程
			
			@Override
			public void run() {
				
				List<PCB> pcbs2 = new ArrayList<>();
				String processMessage2=loadProcess();
				JSONArray jsonArray=JSON.parseArray(processMessage2);
				pcbs2=JSONArray2ObjectList(jsonArray);
				System.out.println("pcb的数量为" + pcbs2.size());
				for(int i=0;i<pcbs2.size();i++){
					currentcreatepcb = pcbs2.get(i);
					readypcbs.add(currentcreatepcb);
					System.out.println("就绪队列的pcb数目" + readypcbs.size());
					try {
						if(i!=pcbs2.size()-1){
							System.out.println("i为" + i + "PCB创建进程" + pcbs2.get(i).getName());
							if(i>1){
								System.out.println("后一个进程的名称为" + pcbs2.get(i+1).getName());
							}
							Thread.sleep((pcbs2.get(i+1).getStartTime()-pcbs2.get(i).getStartTime())*1000 +200);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				currentcreatepcb = null;
				pcb_create.setText("");
				
			}
		}).start();
		
		
		System.out.println("Start!");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){//持续扫描整个就绪队列
					System.out.println("****开始**** " + readypcbs.size());
					currentpcb=null;
					int k=0;
					try {
						Thread.sleep(500);//目的是让创建的新进程先显示在readytable中，再显示在running中
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					//System.out.println(pcbs.size());
					for(int j=0;j<readypcbs.size();j++){
						PCB pcb=readypcbs.get(j);

						if(pcb.getIsOver()==false){
							if(currentpcb==null){
								currentpcb=pcb;
								k=j;
								break;
							}	
						}
					}//从就绪队列中找出需要运行的进程，结束循环
					if(currentpcb!=null){
						readypcbs.remove(k);//从就绪队列中移除该进程
						//reloadJTable(GlobalObject.getjTable());
						currentpcb.setStatus(1);
						currentpcb.decrease();//运行该进程一个单位的时间
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//pcb_running.setText(currentpcb.getName());//在pcb_running中显示运行的进程
						pcb_running.paintImmediately(pcb_running.getBounds());
						System.out.println("正在运行：" + currentpcb.getName());
						allTime++;
						int j =1;
						for(j=1;j<time_piece;j++){//给当前进程运行一个时间片的时间直到进程结束
							if (currentpcb.getIsOver() == false && currentpcb.getStatus() == 1) {
								currentpcb.decrease();//继续运行一个单位的时间
								allTime++;
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}else if(currentpcb.getStatus() == 2){//在这过程中点击了阻塞的按钮
								currentpcb.setStatus(2);
								currentpcb =null;
								break;
							}
						}					
						if(j==time_piece&&currentpcb.getIsOver()==false && currentpcb.getStatus() !=2){//时间片用完了，但是进程还没有结束	
							currentpcb.setStatus(0);
							readypcbs.add(currentpcb);//加到就绪队列的末尾
						}
						
						if(j==time_piece && currentpcb.getIsOver() == true){
							pcb_teminate.setText(currentpcb.getName());
						}
						
					}
					
				}
			}
		}).start();
	}
	
	public static List<PCB> JSONArray2ObjectList(JSONArray jsonArray) {
		List<PCB> pcbs=new ArrayList<>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			PCB pcb=new PCB();
			pcb.Json2Object(jsonObject);
			pcbs.add(pcb);
		}
		return pcbs;
	}
	
	public  static String loadProcess() {
		URL xmlpath=goodtest.class.getClassLoader().getResource("");

		String encoding="utf-8";

		try {
			File file=new File(xmlpath.toString().replace("file:/", "")+"process/JOB.txt");
			if(file.isFile() && file.exists()){ 
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file),encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				StringBuffer buffer=new StringBuffer();
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null){
					buffer.append(lineTxt);
				}
				String message=buffer.toString();
				read.close();
				return message;
			}else{
				System.out.println("打开文件失败");
			}
		} catch (Exception e) {
			System.out.println("加载文件失败");
			e.printStackTrace();
		}

		return null;
	}

}
