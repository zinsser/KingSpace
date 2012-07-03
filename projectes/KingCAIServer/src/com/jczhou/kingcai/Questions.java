package com.jczhou.kingcai;

public class Questions {
	public static class QuestionItem{
		String mAnswer;
		String mContent;
		public QuestionItem(String answer, String content){
			mAnswer = answer;
			mContent = content;
		}
	};
	
	public static QuestionItem CreateQuestionItem(String answer, String content){
		return new QuestionItem(answer, content);
	}
	
	public static final QuestionItem[] sQuestions = {
		CreateQuestionItem("A", "1．湘西某县有68万人口，各民族所占比例如图1所示，则该县少数民族人口共有（　　）\nA．30.0万  B．37.4万\nC．30.6万  D．40.0万"),
		CreateQuestionItem("B", "2．一道选择题共有四个答案，其中只有一个是正确的．有一位同学在没有把握的情况下，随意地选了一个答案，那么他选对的频率是（　　）\nA．100％  B．50％  C．30％  D．25％"),
		CreateQuestionItem("C", "3．东门中学有学生对到浏阳大围山旅游的游客进行了统计：10天中，有3天每天的游客人数为400人，有2天每天的游客人数为500人，有5天每天的游客人数为300人，那么10天中平均每天的游客人数为（　　）\nA．400  B．350  C．370  D．420"),
		CreateQuestionItem("D", "4．为了筹备班级联欢会，班长对全班50名同学喜欢吃哪几种水果作了民意调查，小明将班长的统计结果绘成如下的统计图2，并得出四个结论，其中错误的是（　　）\nA．一人可以喜欢吃几种水果\nB．喜欢吃葡萄的人最多\nC．喜欢吃苹果的人数是喜欢吃梨人数的3倍\nD．喜欢吃香蕉的人数占全班人数的20％"),
		CreateQuestionItem("A", "5．一次考试后对60名学生的成绩进行频率分布统计，以10分为一分数段，共分10组，若学生得分均为整数，且在69.5～79.5之间这组的频率是0.3，那么得分在这个分数段的学生有（　　）\nA．30人  B．18人  C．20人  D．15人"),
		CreateQuestionItem("B", "6．如图3，一圆柱高8cm，底面半径2cm，一只蚂蚁从点A爬到点B处吃食，要爬行的最短路程(π取3)是（　　）\nA．20cm   B．10cm   C．14cm   D．无法确定"),
		CreateQuestionItem("B", "7．一家鞋店在一段时间内销售了某种女式鞋子38双，其中各种尺码的鞋的销售量如下表：\n鞋的尺码（单位：cm） 22.5 23 23.5 24 24.5\n销售量（单位：双） 3 6 12 9 8\n根据统计的数据，鞋店进货时尺寸码为23cm，23.5cm，24cm的鞋双数合理的比是（　　）\nA．1∶2∶4  B．2∶4∶5  C．2∶4∶3  D．2∶3∶4"),
		CreateQuestionItem("B", "8．“数学专页报真是同学们学习数学的好帮手”，在这句话中“学”字出现的频数是  "),
		CreateQuestionItem("B", "9．若一个直角三角形的三边长分别为3、4、x，则满足此三角形的x值为（　　）\nA．    B．   C． 或   D．没有"),
		CreateQuestionItem("B", "10．小晴的妈妈买了一部29英寸(74cm)的电视机，下列对29英寸的说法中正确的是（　　）\nA．小晴认为指的是屏幕的长度    B．小晴的妈妈认为指的是屏幕的宽度\nC．小晴的爸爸认为指的是屏幕的周长  \nD．售货员认为指的是屏幕对角线的长度"),
		CreateQuestionItem("B", "11．如图4，四边形ABCD中，AD=DC，∠ADC=∠ABC=90°，DE⊥AB，若四边形ABCD面积为16，则DE的长为（　　）\nA．3  B．2  C．4  D．8")
	};
}
