
public class Generator {

	public static void main(String[] args) {
		/*
		 *	����y�{�ܧ�ت��G
		 *		�i�����e�H���v�έp�覡�o���������礤���h�֤�Ҫ��ϥΪ̷|�ŦX�ϥΪ��@�N�e�Ԯɶ�(tolerance time)�C
		 *	����y�{�z�ѡG
		 *		�i�����ɨϥΪ̪� arrival ���ɭԨӤӧַ|�ɭPVM�����ƶq�Y��W�h(scaling rule)�Ӥ��Τ����ϥΪ̦欰�C
		 *			�y���Ƥj�q�ɶ��b���͹�����(data set)�A�u�n�����ƨS�����ͦn�N�|�ɭP���祢�ѡA�ӥB�o�إ��ѬO�L�k�w�����D���C
		 *				
		 *	�ﵽ��k�����G
		 *		�Q�Ϋ~�Ⱥ޲z3��$\delta$�������A�N���ͪ�����I(��ӨϥΪ̩�F�t�ζ��j�ɶ�)��J�t�γB�z�ɶ�(System process time)�A
		 *
		 *	���D���R�G
		 *		�ϥΪ̦b��F�t�Τ����i�ౡ��������ءG
		 *			�i�ౡ�p�@�G
		 * 						�p�G�A�ϥΪ̩�F�t�ζ��j�ɶ���t�γB�z�ɶ��٭n�u�F�o�N��|���ϥΪ̦b�t�Τ�����(�ϥΪ̳B�z�ɶ�=���Ԯɶ�+�t�γB�z�ɶ�)�C
		 *			�i�ౡ�p�G�G
		 *						�p�G�A�ϥΪ̩�F�t�ζ��j�ɶ���t�γB�z�ɶ��٭n���G�o�N��|���ϥΪ̤��|����(�ϥΪ̳B�z�ɶ�=�t�γB�z�ɶ�)
		 *
		 *			�S�ұ��p�T�G
		 *						��t�γB�z�ɶ��ܲ��{�צs�b�ɤ~���o�ر��p�A�Ҧp�G�t�γ̵u�B�z�ɶ���136��B�t�γ̱`�B�z�ɶ���305��C
		 *							�p�G�A�ϥΪ̩�F�ɷ|�]���t�γB�z�ɶ���í�w����]�A���i��ϥΪ̷|�o�͵��ݩάO�����ݡC
		 *						"���O"�A��t�γB�z�ɶ�í�w�ɡA�Ҧp�G�C������ɶ����O150��A�N���|���o�ر��ΡC
		 *
		 *		�t�ΩҦ����i�ౡ�p���O�i�ౡ�p�@�M�i�ౡ�p�G�Ҳզ��A�u�O�b���P����F�t�v�U��إi�ౡ�p�Ҧ�����Ҥ��P�C
		 *			�|�Ҩӻ��G���]�ݭn��������U��90% �O�i�H�ŦX�� tolerance time�A�h�i�ౡ�p�G���񭫬�0.9�A�i���p�@���񭫬�0.1
		 *
		 *	�ﵽ��k�����G		
		 *		�p�G�n�J�A���礧�e�����D�������礤���h�֤�Ҫ��ϥΪ̷|�ŦX�ϥΪ��@�N�e�Ԯɶ�(tolerance time)�����p�C
		 *			�i�H���w�i�ౡ�p�@�M�i�ౡ�p�G�e�Ҧ�����I����ҡA�M��A�Q�β��;��H���s�y�X�ϥΪ̹�ڭn�i�J�t�Ϊ��ȡC
		 *				�o�ظ�Ʋ����I���覡��w���ϥΪ̦b�������礤�ŦX�@�N�e�ԭȶ�����Ҩåi�H�[��X�U�C�T�ر��p�G
		 *					(1)�t�Φb�ܦ��L(�i�ౡ�p�@��Ҹ���)�B
		 *					(2)�t�Φb���`���L(��إi�ౡ�p��ҬۦP)��
		 *					(3)�t�ΪŶ�(�i�ౡ�p�G��Ҹ���)���Y��W�h(scaling rule)�欰�C
		 *
		 *  �ݭn�d�N�άO��ߪ��a��:
		 *		���覡���ͪ���Ƥ��t�O�_�̵M�ŦX�ϥΪ̩�F���j�ɶ��A�q���Ƥ��t�Ѽ�\lambda�A�ݭn������ҡC
		 */
		 
			/*
			 *   ���;��]�p�y�{�G
			 *		Step.1:�]�w���窺�ϥΪ̼ƶq
			 *		Step.2:�]�wcase1, case2�����(���Case����Q�`�M��1)
			 *			Step.2-1:�p��case1�Mcase2�ݭn���ϥΪ̼ƶq(�H�ơA�ĵL������)
			 *		Step.3:����case1�Mcase2�һݭn���H��
			 *			Step.3-1: 
			 *				Pr(�t�γB�z�ɶ��϶��W�� < �ϥΪ̩�F���j�ɶ� < �t�γB�z�ɶ��϶��W��)=case1�����
			 *				Pr( �t�γB�z�ɶ��϶��U��<�ϥΪ̩�F���j�ɶ�)=case2����� �� Pr( �t�γB�z�ɶ��϶��W��>�ϥΪ̩�F���j�ɶ�)=case2
			 *		Step.4:���Ҳ��ͪ���F���t�O�_�M�ź٬۲�
			 *
			 *	�ɥR�G exponential distribution with parameter \lambda(\lambda e^{\lambda x})
			 *	���ɡG �ֿn���F(x)=y=���v��,���v���f(x)= \lambda e^{-\lambda x}
			 *				F(x)=y = �n��f(x)�A�d��0<X<x
			 *				y= \lambda e^{-\lambda x} ; �n��f(x)�A�d��0<X<x
			 *				y= - e^(-\lambda x)+1
			 *				y= 1 - \lambda e^(-\lambda x) //�ֿn���v���
			 */				  
						/*
						 *  �Dy���Ϩ��:
						 *   y=1-e^{- \lambda x}
						 *   y-1=-e^{- \lambda x}
						 *	 1-y=e^{- \lambda x}
						 *   x=-(1/ \lambda) ln(1-y) //���w�@�Ӿ��v��y�A�i�H�o��U�@�ӨϥΪ̭n��F�t�Ϊ����
						 */

		/*
		 *  �ѼƳ]�w�ŧi
		 */
		System.out.println("=====�}�l�i��ѼƳ]�w=====");
		
		/*
		 * Step.0: �]�w�t�Υ����B��ɶ�95%�H��϶��W���P�U��
		 */
			double Upper =-1;
			double Lowwer =0;
			while(Upper<Lowwer)
			{
			java.util.Scanner UpperBoundSystemProcessTimeInput = new
				java.util.Scanner(System.in);
			System.out.println("�п�J�t�Υ����B��ɶ�95%�H��϶��W��");
			double UpperBoundSystemProcessTime = UpperBoundSystemProcessTimeInput.nextDouble();
			java.util.Scanner LowwerBoundSystemProcessTimeInput = new
					java.util.Scanner(System.in);			
			System.out.println("�п�J�t�Υ����B��ɶ�95%�H��϶��U��");
			double LowwerBoundSystemProcessTime = LowwerBoundSystemProcessTimeInput.nextDouble();
			System.out.println("�z��J���t��95%�����B��ɶ��H��϶��W���P�U����");
			System.out.print("(�W��,�U��)=");
			System.out.print("(");
			System.out.print(UpperBoundSystemProcessTime);
			System.out.print(",");
			System.out.print(LowwerBoundSystemProcessTime);
			System.out.println(")");
			System.out.print("�t�Υ����A�ȳt�װ϶���");
			System.out.print("(");
			System.out.print(1/UpperBoundSystemProcessTime);
			System.out.print(",");
			System.out.print(1/LowwerBoundSystemProcessTime);
			System.out.print(")");
				Lowwer=LowwerBoundSystemProcessTime;
				Upper=UpperBoundSystemProcessTime;
				if(UpperBoundSystemProcessTime<LowwerBoundSystemProcessTime)
				{
					System.out.println("�z��J���H��϶��W���P�U�����~�A�Э��s�T�{�C");			
				}
			
			}
			/*
				 * �T�O�W�U���޿��J���T
				 */
		/*
		 * Step.1:�]�w���窺�ϥΪ̼ƶq�P�t�v
		 */
			java.util.Scanner SamplingNumInput = new
					java.util.Scanner(System.in);
			System.out.println("�п�J�`����H��");
				int SamplingNum = SamplingNumInput.nextInt();
				System.out.print("�z��J�`����H�ơG");
				System.out.println(SamplingNum);	
			
				java.util.Scanner LambdaInput = new
						java.util.Scanner(System.in);
					System.out.println("�п�J�����ϥΪ̩�F�t�v(���G��/��)");
					double Lambda = LambdaInput.nextDouble();
		/*
		 * Step.2:�]�wcase1, case2�Mcase3�����(�T��Case������`�M��1)���ˬd�`�X�O�_��1
		 */

				float RateCheck = 0;
				while(RateCheck !=1)
				{
			/*
			 *  �]�wcase1�����
			 */
			java.util.Scanner Case1RateInput = new
					java.util.Scanner(System.in);
			System.out.println("�п�J�ϥΪ̷|�o�͵��ݪ����");
				float Case1Rate = Case1RateInput.nextFloat();
				System.out.print("�п�J�ϥΪ̷|�o�͵��ݪ���ҡG");
				System.out.println(Case1Rate);	
				/*
				 *  �]�wcase2�����
				 */
				java.util.Scanner Case2RateInput = new
						java.util.Scanner(System.in);
				System.out.println("�п�J�ϥΪ̤��|�o�͵��ݪ����");
					float Case2Rate = Case2RateInput.nextFloat();
					System.out.print("�п�J�ϥΪ̤��|�o�͵��ݪ���ҡG");
					System.out.println(Case2Rate);	

				/*
				 *  �]�wcase3�����
				 */
				java.util.Scanner Case3RateInput = new
						java.util.Scanner(System.in);
				System.out.println("�п�J�L�k�x���ϥΪ̵��ݦ欰�����");
					float Case3Rate = Case3RateInput.nextFloat();
					System.out.print("�п�J�L�k�x���ϥΪ̵��ݦ欰����ҡG");
					System.out.println(Case3Rate);	
				
				/*
				 *  �ˬd�]�w��ҬO�_���T
				 */
				RateCheck = Case1Rate + Case2Rate + Case3Rate;
				if(RateCheck>1|RateCheck<0)
				{
				System.out.print("�A��J������`�M���G");
				System.out.println(RateCheck);
				System.out.print("�Э��s�T�{�U��case�����");
				}
				if(0<=RateCheck&RateCheck<=1)
				{
				System.out.println("�A��J������`�M���G");
				System.out.print("�A��J��case1����Ҭ��G");
				System.out.println(Case1Rate);
				System.out.print("�A��J��case2����Ҭ��G");
				System.out.println(Case2Rate);
				System.out.print("�A��J��case3����Ҭ��G");
				System.out.println(Case3Rate);
				System.out.println("�U�רҤ�ҳ]�w�����C");
				}


		/*
		 * Step.3:�p��case1�Mcase2�ϥΪ̼ƶq
		 */		
				/*
				 *  �]�wcase1��˪��ƶq
				 */
				
				float NumCase1 = (float)SamplingNum* Case1Rate;
				Math.ceil(NumCase1);
				System.out.print("case1����˼Ƭ��G");
				System.out.println((int)NumCase1);

				/*
				 *  �]�wcase2��˪��ƶq
				 */
				
				float NumCase2 = (float)SamplingNum* Case2Rate;
				Math.ceil(NumCase2);
				System.out.print("case2����˼Ƭ��G");
				System.out.println((int)NumCase2);

				/*
				 *  �]�wcase3��˪��ƶq
				 */
				float NumCase3 = (float)SamplingNum* Case3Rate;
				
				Math.ceil(NumCase3);
				System.out.print("case3����˼Ƭ��G");
				System.out.println((int)NumCase3);

				System.out.println("=====�Ҧ��i��ѼƳ]�w����=====");				
				/*
				 * Step.3-1:���case1�˥�
				 */
				int i =0;
				int j =0;
				int k =0;
				
				for(i = 1;i<=(int)NumCase1;i++)
				{
					/*
					 * �H�����;��v
					 */
					double RandomCase1 = Math.random(); //��0~1���ֿn���v��
					double ProbabilityCase1 = RandomCase1; //���v��
					double SecondCase1=Lowwer*2; //�ϥΪ̩�F���j�ɶ��ALowwer*2�u�O������While�Ұʱ��󪺪�l��
					int count=0; //��˭p�ƾ�
					
					while(SecondCase1>Lowwer) //�ϥΪ̶i�J�t�ήɷ|�ƶ�
					{
						count=count+1;
						
					SecondCase1 = (-1/Lambda)*Math.log(1-ProbabilityCase1);
						// Math.log(a) �N�O ln(a)
						// Second=-(1/ \lambda) ln(1-y)	
						
					System.out.print("***Case1�����");
					System.out.print(count);
					System.out.print("��");
					System.out.println("���***");
					}
					System.out.print("Case1��");
					System.out.print(i);
					System.out.print("�ӨϥΪ̩�F�t�ήɶ���");
					System.out.print(SecondCase1);
					System.out.print("��");
					System.out.print("�A���v�Ȭ�");
					System.out.println(ProbabilityCase1);
				}
				
				/*
				 * Step.3-2:���case2�˥�
				 */	
				for(j = 1;j<=(int)NumCase2;j++)
				{
					/*
					 * �H�����;��v
					 */
					double RandomCase2 = Math.random(); //��0~1���ֿn���v��
					double ProbabilityCase2 = RandomCase2; //���v��
					double SecondCase2=Upper/2; //�ϥΪ̩�F���j�ɶ��AUpper/2�u�O������While�Ұʱ��󪺪�l��
					int count=0; //��˭p�ƾ�
					

					while(SecondCase2<Upper) //�ϥΪ̶i�J�t�ήɤ��|�ƶ�
					{
						count=count+1;
						
					SecondCase2 = (-1/Lambda)*Math.log(1-ProbabilityCase2);
						// Math.log(a) �N�O ln(a)
						// Second=-(1/ \lambda) ln(1-y)	
					
					System.out.print("***Case2�����");
					System.out.print(count);
					System.out.print("��");
					System.out.println("���***");
					}
					System.out.print("Case2��");
					System.out.print(i);
					System.out.print("�ӨϥΪ̩�F�t�ήɶ���");
					System.out.print(SecondCase2);
					System.out.print("��");
					System.out.print("�A���v�Ȭ�");
					System.out.println(ProbabilityCase2);
				}				
				/*
				 * Step.3-3:���case3�˥�
				 */
				for(k = 1;k<=(int)NumCase3;k++) 
				{
					/*
					 * �H�����;��v
					 */
					double RandomCase3 = Math.random(); //��0~1���ֿn���v��
					double ProbabilityCase3 = RandomCase3; //���v��
					double SecondCase3=(Upper+Lowwer)/2; //�ϥΪ̩�F���j�ɶ��A(Upper+Lowwer)/2�u�O������While�Ұʱ��󪺪�l��
					int count=0; //��˭p�ƾ�
					
					while(SecondCase3<Upper&SecondCase3>Lowwer) //�ϥΪ̶i�J�t�ήɤ����D�|���|�ƶ�
					{
						count=count+1;						
						
					SecondCase3 = (-1/Lambda)*Math.log(1-ProbabilityCase3);
						// Math.log(a) �N�O ln(a)
						// Second=-(1/ \lambda) ln(1-y)	
					
					System.out.print("***Case3�����");
					System.out.print(count);
					System.out.print("��");
					System.out.println("���***");

					}
					System.out.print("Case3��");
					System.out.print(i);
					System.out.print("�ӨϥΪ̩�F�t�ήɶ���");
					System.out.print(SecondCase3);
					System.out.print("��");
					System.out.print("�A���v�Ȭ�");
					System.out.println(ProbabilityCase3);
				}
			}

	}
}
