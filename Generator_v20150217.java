
public class Generator {

	public static void main(String[] args) {
		/*
		 *	實驗流程變更目的：
		 *		進行實驗前以機率統計方式得知長期試驗中有多少比例的使用者會符合使用者願意容忍時間(tolerance time)。
		 *	實驗流程理由：
		 *		進行實驗時使用者的 arrival 有時候來太快會導致VM機器數量縮放規則(scaling rule)來不及反應使用者行為。
		 *			造成化大量時間在產生實驗資料(data set)，只要實驗資料沒有產生好就會導致實驗失敗，而且這種失敗是無法預先知道的。
		 *				
		 *	改善方法概念：
		 *		利用品值管理3倍$\delta$的概念，將產生的資料點(兩個使用者抵達系統間隔時間)丟入系統處理時間(System process time)，
		 *
		 *	問題分析：
		 *		使用者在抵達系統中的可能情型分為兩種：
		 *			可能情況一：
		 * 						如果，使用者抵達系統間隔時間比系統處理時間還要短；這代表會有使用者在系統中等候(使用者處理時間=等候時間+系統處理時間)。
		 *			可能情況二：
		 *						如果，使用者抵達系統間隔時間比系統處理時間還要長：這代表會有使用者不會等待(使用者處理時間=系統處理時間)
		 *
		 *			特例情況三：
		 *						當系統處理時間變異程度存在時才有這種情況，例如：系統最短處理時間為136秒、系統最常處理時間為305秒。
		 *							如果，使用者抵達時會因為系統處理時間不穩定的原因，有可能使用者會發生等待或是不等待。
		 *						"但是"，當系統處理時間穩定時，例如：每次執行時間都是150秒，就不會有這種情形。
		 *
		 *		系統所有的可能情況階是可能情況一和可能情況二所組成，只是在不同的抵達速率下兩種可能情況所佔的比例不同。
		 *			舉例來說：假設需要長期實驗下有90% 是可以符合的 tolerance time，則可能情況二的比重為0.9，可情況一的比重為0.1
		 *
		 *	改善方法概念：		
		 *		如果要克服實驗之前不知道長期試驗中有多少比例的使用者會符合使用者願意容忍時間(tolerance time)的情況。
		 *			可以指定可能情況一和可能情況二占所有資料點的比例，然後再利用產生器隨機製造出使用者實際要進入系統的值。
		 *				這種資料產生點的方式能預期使用者在長期試驗中符合願意容忍值間的比例並可以觀察出下列三種情況：
		 *					(1)系統在很忙碌(可能情況一比例較高)、
		 *					(2)系統在正常忙碌(兩種可能情況比例相同)至
		 *					(3)系統空閒(可能情況二比例較高)的縮放規則(scaling rule)行為。
		 *
		 *  需要留意或是擔心的地方:
		 *		此方式產生的資料分配是否依然符合使用者抵達間隔時間服從指數分配參數\lambda，需要實際驗證。
		 */
		 
			/*
			 *   產生器設計流程：
			 *		Step.1:設定實驗的使用者數量
			 *		Step.2:設定case1, case2的比例(兩種Case的比利總和為1)
			 *			Step.2-1:計算case1和case2需要的使用者數量(人數，採無條件近位)
			 *		Step.3:產生case1和case2所需要的人數
			 *			Step.3-1: 
			 *				Pr(系統處理時間區間上限 < 使用者抵達間隔時間 < 系統處理時間區間上限)=case1的比例
			 *				Pr( 系統處理時間區間下限<使用者抵達間隔時間)=case2的比例 或 Pr( 系統處理時間區間上限>使用者抵達間隔時間)=case2
			 *		Step.4:驗證產生的抵達分配是否和宣稱相符
			 *
			 *	補充： exponential distribution with parameter \lambda(\lambda e^{\lambda x})
			 *	推導： 累積函數F(x)=y=機率值,機率函數f(x)= \lambda e^{-\lambda x}
			 *				F(x)=y = 積分f(x)，範圍0<X<x
			 *				y= \lambda e^{-\lambda x} ; 積分f(x)，範圍0<X<x
			 *				y= - e^(-\lambda x)+1
			 *				y= 1 - \lambda e^(-\lambda x) //累積機率函數
			 */				  
						/*
						 *  求y的反函數:
						 *   y=1-e^{- \lambda x}
						 *   y-1=-e^{- \lambda x}
						 *	 1-y=e^{- \lambda x}
						 *   x=-(1/ \lambda) ln(1-y) //給定一個機率值y，可以得到下一個使用者要抵達系統的秒數
						 */

		/*
		 *  參數設定宣告
		 */
		System.out.println("=====開始進行參數設定=====");
		
		/*
		 * Step.0: 設定系統平均運行時間95%信賴區間上限與下限
		 */
			double Upper =-1;
			double Lowwer =0;
			while(Upper<Lowwer)
			{
			java.util.Scanner UpperBoundSystemProcessTimeInput = new
				java.util.Scanner(System.in);
			System.out.println("請輸入系統平均運行時間95%信賴區間上限");
			double UpperBoundSystemProcessTime = UpperBoundSystemProcessTimeInput.nextDouble();
			java.util.Scanner LowwerBoundSystemProcessTimeInput = new
					java.util.Scanner(System.in);			
			System.out.println("請輸入系統平均運行時間95%信賴區間下限");
			double LowwerBoundSystemProcessTime = LowwerBoundSystemProcessTimeInput.nextDouble();
			System.out.println("您輸入的系統95%平均運行時間信賴區間上限與下限為");
			System.out.print("(上限,下限)=");
			System.out.print("(");
			System.out.print(UpperBoundSystemProcessTime);
			System.out.print(",");
			System.out.print(LowwerBoundSystemProcessTime);
			System.out.println(")");
			System.out.print("系統平均服務速度區間為");
			System.out.print("(");
			System.out.print(1/UpperBoundSystemProcessTime);
			System.out.print(",");
			System.out.print(1/LowwerBoundSystemProcessTime);
			System.out.print(")");
				Lowwer=LowwerBoundSystemProcessTime;
				Upper=UpperBoundSystemProcessTime;
				if(UpperBoundSystemProcessTime<LowwerBoundSystemProcessTime)
				{
					System.out.println("您輸入的信賴區間上限與下限有誤，請重新確認。");			
				}
			
			}
			/*
				 * 確保上下限邏輯輸入正確
				 */
		/*
		 * Step.1:設定實驗的使用者數量與速率
		 */
			java.util.Scanner SamplingNumInput = new
					java.util.Scanner(System.in);
			System.out.println("請輸入總實驗人數");
				int SamplingNum = SamplingNumInput.nextInt();
				System.out.print("您輸入總實驗人數：");
				System.out.println(SamplingNum);	
			
				java.util.Scanner LambdaInput = new
						java.util.Scanner(System.in);
					System.out.println("請輸入平均使用者抵達速率(單位：個/秒)");
					double Lambda = LambdaInput.nextDouble();
		/*
		 * Step.2:設定case1, case2和case3的比例(三種Case的比例總和為1)並檢查總合是否為1
		 */

				float RateCheck = 0;
				while(RateCheck !=1)
				{
			/*
			 *  設定case1的比例
			 */
			java.util.Scanner Case1RateInput = new
					java.util.Scanner(System.in);
			System.out.println("請輸入使用者會發生等待的比例");
				float Case1Rate = Case1RateInput.nextFloat();
				System.out.print("請輸入使用者會發生等待的比例：");
				System.out.println(Case1Rate);	
				/*
				 *  設定case2的比例
				 */
				java.util.Scanner Case2RateInput = new
						java.util.Scanner(System.in);
				System.out.println("請輸入使用者不會發生等待的比例");
					float Case2Rate = Case2RateInput.nextFloat();
					System.out.print("請輸入使用者不會發生等待的比例：");
					System.out.println(Case2Rate);	

				/*
				 *  設定case3的比例
				 */
				java.util.Scanner Case3RateInput = new
						java.util.Scanner(System.in);
				System.out.println("請輸入無法掌握使用者等待行為的比例");
					float Case3Rate = Case3RateInput.nextFloat();
					System.out.print("請輸入無法掌握使用者等待行為的比例：");
					System.out.println(Case3Rate);	
				
				/*
				 *  檢查設定比例是否正確
				 */
				RateCheck = Case1Rate + Case2Rate + Case3Rate;
				if(RateCheck>1|RateCheck<0)
				{
				System.out.print("你輸入的比例總和為：");
				System.out.println(RateCheck);
				System.out.print("請重新確認各種case的比例");
				}
				if(0<=RateCheck&RateCheck<=1)
				{
				System.out.println("你輸入的比例總和為：");
				System.out.print("你輸入的case1的比例為：");
				System.out.println(Case1Rate);
				System.out.print("你輸入的case2的比例為：");
				System.out.println(Case2Rate);
				System.out.print("你輸入的case3的比例為：");
				System.out.println(Case3Rate);
				System.out.println("各案例比例設定完成。");
				}


		/*
		 * Step.3:計算case1和case2使用者數量
		 */		
				/*
				 *  設定case1抽樣的數量
				 */
				
				float NumCase1 = (float)SamplingNum* Case1Rate;
				Math.ceil(NumCase1);
				System.out.print("case1的抽樣數為：");
				System.out.println((int)NumCase1);

				/*
				 *  設定case2抽樣的數量
				 */
				
				float NumCase2 = (float)SamplingNum* Case2Rate;
				Math.ceil(NumCase2);
				System.out.print("case2的抽樣數為：");
				System.out.println((int)NumCase2);

				/*
				 *  設定case3抽樣的數量
				 */
				float NumCase3 = (float)SamplingNum* Case3Rate;
				
				Math.ceil(NumCase3);
				System.out.print("case3的抽樣數為：");
				System.out.println((int)NumCase3);

				System.out.println("=====所有進行參數設定結束=====");				
				/*
				 * Step.3-1:抽取case1樣本
				 */
				int i =0;
				int j =0;
				int k =0;
				
				for(i = 1;i<=(int)NumCase1;i++)
				{
					/*
					 * 隨機產生機率
					 */
					double RandomCase1 = Math.random(); //取0~1的累積機率值
					double ProbabilityCase1 = RandomCase1; //機率值
					double SecondCase1=Lowwer*2; //使用者抵達間隔時間，Lowwer*2只是為滿足While啟動條件的初始值
					int count=0; //抽樣計數器
					
					while(SecondCase1>Lowwer) //使用者進入系統時會排隊
					{
						count=count+1;
						
					SecondCase1 = (-1/Lambda)*Math.log(1-ProbabilityCase1);
						// Math.log(a) 就是 ln(a)
						// Second=-(1/ \lambda) ln(1-y)	
						
					System.out.print("***Case1執行第");
					System.out.print(count);
					System.out.print("次");
					System.out.println("抽樣***");
					}
					System.out.print("Case1第");
					System.out.print(i);
					System.out.print("個使用者抵達系統時間於");
					System.out.print(SecondCase1);
					System.out.print("秒");
					System.out.print("，機率值為");
					System.out.println(ProbabilityCase1);
				}
				
				/*
				 * Step.3-2:抽取case2樣本
				 */	
				for(j = 1;j<=(int)NumCase2;j++)
				{
					/*
					 * 隨機產生機率
					 */
					double RandomCase2 = Math.random(); //取0~1的累積機率值
					double ProbabilityCase2 = RandomCase2; //機率值
					double SecondCase2=Upper/2; //使用者抵達間隔時間，Upper/2只是為滿足While啟動條件的初始值
					int count=0; //抽樣計數器
					

					while(SecondCase2<Upper) //使用者進入系統時不會排隊
					{
						count=count+1;
						
					SecondCase2 = (-1/Lambda)*Math.log(1-ProbabilityCase2);
						// Math.log(a) 就是 ln(a)
						// Second=-(1/ \lambda) ln(1-y)	
					
					System.out.print("***Case2執行第");
					System.out.print(count);
					System.out.print("次");
					System.out.println("抽樣***");
					}
					System.out.print("Case2第");
					System.out.print(i);
					System.out.print("個使用者抵達系統時間於");
					System.out.print(SecondCase2);
					System.out.print("秒");
					System.out.print("，機率值為");
					System.out.println(ProbabilityCase2);
				}				
				/*
				 * Step.3-3:抽取case3樣本
				 */
				for(k = 1;k<=(int)NumCase3;k++) 
				{
					/*
					 * 隨機產生機率
					 */
					double RandomCase3 = Math.random(); //取0~1的累積機率值
					double ProbabilityCase3 = RandomCase3; //機率值
					double SecondCase3=(Upper+Lowwer)/2; //使用者抵達間隔時間，(Upper+Lowwer)/2只是為滿足While啟動條件的初始值
					int count=0; //抽樣計數器
					
					while(SecondCase3<Upper&SecondCase3>Lowwer) //使用者進入系統時不知道會不會排隊
					{
						count=count+1;						
						
					SecondCase3 = (-1/Lambda)*Math.log(1-ProbabilityCase3);
						// Math.log(a) 就是 ln(a)
						// Second=-(1/ \lambda) ln(1-y)	
					
					System.out.print("***Case3執行第");
					System.out.print(count);
					System.out.print("次");
					System.out.println("抽樣***");

					}
					System.out.print("Case3第");
					System.out.print(i);
					System.out.print("個使用者抵達系統時間於");
					System.out.print(SecondCase3);
					System.out.print("秒");
					System.out.print("，機率值為");
					System.out.println(ProbabilityCase3);
				}
			}

	}
}
