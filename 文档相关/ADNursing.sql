/*
Source Host           : localhost:3306
Source Database       : adnursing
Target Server Type    : MYSQL


Date: 2016-07-08
*/

drop database if exists adnursing;

create database adnursing;

use adnursing;

SET FOREIGN_KEY_CHECKS=0;


-- ----------------------------
-- Table structure for user
-- ----------------------------

DROP TABLE IF exists user;
CREATE TABLE user (
  user_id int(10) NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  userpass varchar(100) NOT NULL,
  img_url varchar(500) NOT NULL,
  gender varchar(11) DEFAULT NULL,
  address varchar(100) DEFAULT NULL,
  description varchar(30) DEFAULT NULL,
  tested_person varchar(100) DEFAULT NULL,
  tested_birth varchar(100) DEFAULT NULL,
  tested_gender varchar(100) NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY username (username)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------




-- ----------------------------
-- Table structure for testres
-- ----------------------------

DROP TABLE IF exists test;
CREATE TABLE test (
  test_id int(10) NOT NULL AUTO_INCREMENT,
  owner_id int(11) NOT NUll,
  test_type varchar(10) NOT NULL,
  test_point int(10) NOT NULL,
  test_date  date NOT NULL,
  PRIMARY KEY (test_id),
  KEY owner_id (owner_id),
  CONSTRAINT test_ibfk_1 FOREIGN KEY (owner_id) REFERENCES user (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------




-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS post;
CREATE TABLE post (
  post_id int(11) NOT NULL AUTO_INCREMENT,
  post_title varchar(20) NOT NULL,
  post_text varchar(500) NOT NULL,
  post_kind varchar(10) NOT NULL,
  post_date datetime NOT NULL,
  comment_num int(11) unsigned NOT NULL DEFAULT '0',
  owner_id int(11) NOT NUll,
  img_url varchar(500) NOT NULL,
  PRIMARY KEY (post_id),
  KEY owner_id (owner_id),
  CONSTRAINT post_ibfk_1 FOREIGN KEY (owner_id) REFERENCES user (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO 'post' VALUES (
  '1', 
  '老年痴呆患者的症状会有哪些',
  '随着人口的老年化进程不断加快，老年痴呆症的发病率日趋增高，那老年痴呆症患者的症状有哪些呢？',
  '病症疑问',
  '2016-07-14 00:49:18',
  '10',
  '1',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '2', 
  '有关阿尔茨海默症患者行为',
  '患者总是会有一些重复性行为，请问这是什么原因呢？',
  '病症疑问',
  '2016-07-10',
  '34',
  '4',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '3', 
  '名医推荐: 王鲁宁',
  '1986年毕业于第四军医大学，89年获第四军医大学硕士学位，99年获军医进修学院博士学位。医学基础扎实，专业理论雄厚，对技术精益求精，对病人高度负责，擅长老年神经病，特别是老年期痴呆及帕金森病的诊断和治疗。科研能力强，参加多项军队及国家级科研课题的工作。在“APA微囊化人工细胞临床应用研究”中，完成了偏侧帕金森病样动物嗜铬细胞脑内移植工作，达国际研究水平。
曾获军队科技进步奖二等奖1项，专利2项。近2年发表论文9篇，其中论文“Cerebral Amyloid Angiopathy With Dementia Clinicopathological Studies Of 17 Cases”在杂志Chinese Medical Journal上以第一作者发表并被Sci收录。1999年被评为总医院科技新秀，并荣立三等功。擅长老年痴呆，帕金森病，脑血管病，睡眠障碍及相关疾患的诊治。',
  '名医推荐',
  '2016-07-12',
  '2343',
  'mysql',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '4', 
  '名医推荐: 邵明',
  '邵明，男，湖南常德人，工作于广医一院。主任医师，神经病学博士，神经外科博士后，神经内科主任，硕士导师，毕业于中山医科大学，1998年至2001年曾在首都医科大学宣武医院博士后后流动站工作从事帕金森病手术治疗的研究。是中华医学会帕金森病与运动障碍病学组委员，担任广东省医学会帕金森病与运动障碍病学组副组长。',
  '名医推荐',
  '2016-07-13',
  '2565',
  'mysql',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '5', 
  '药物推荐：加兰他敏',
  '加兰他敏（galantamine），口服，一日2次，建议与早餐及晚餐同服。起始剂量: 推荐剂量为一次4mg，一日2次。治疗过程中保证足够液体摄入。医师在对患者临床疗效及耐受性进行综合评价后，可以将剂量逐渐提高到临床最高推荐剂量，一次12mg，一日2次。
胆碱酯酶抑制剂一般耐受良好，但常见胃肠道不良反应如恶心、腹泻和呕吐，有时可能会导致部分患者停药。',
  '药物推荐',
  '2016-07-08',
  '233',
  '2',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '6', 
  '药物推荐：盐酸美金刚',
  '盐酸美金刚（memantine）是N-甲基-天冬氨酸(NMDA)受体激动剂，目前也已批准用于AD。其药物机制尚未完全清楚，可能与其非竞争性地激动NMDA受体，从而保护胆碱能神经元免受兴奋性氨基酸毒性破坏有关。可用于中晚期AD患者，研究显示对中重度患者整体转归、日常生活能力和行为有明显作用，其中妄想、激越或攻击性和易激惹是改善最明显的症状。该药的不良反应较少，包括幻觉、意识模糊、头晕、头痛等。发生率低的不良反应有焦虑、肌张力增加、呕吐、膀胱炎、性欲增加。为了减少副作用的发生，应注意逐渐加量达到维持剂量：自每日5mg（半片，晨服）起，每周递增5mg剂量，每日最大剂量20mg。
美金刚与胆碱酯酶抑制剂联合用药可能比单独应用胆碱酯酶抑制剂更有效，但还需进一步研究证实。',
  '药物推荐',
  '2016-07-13',
  '2333',
  '13',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '7', 
  '阿尔茨海默病容易并发哪些疾病',
  '对阿尔茨海默病性痴呆患者，家人往往很容易产生过度的保护倾向，这是造成病人卧床不起的最大原因。患者一旦卧床不起，可出现许多并发症，这将会加重痴呆症状，加快缩短其寿命。常见并发症包括：
1、饮食过度或不足，引起胃肠道不适、出血，甚至穿孔。
2、水电解质紊乱。
3、因吞咽困难，易并发吸入性肺炎或窒息。
4、长期卧床易发生褥疮、便秘或血栓、栓塞性疾病。
5、外伤或骨折。
6、大、小便失控，易致泌尿道感染。',
  '交流分享',
  '2016-07-12',
  '213',
  '6',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '8', 
  '如何正确做到预防老年痴呆',
  '对于老年人来说，老年痴呆已成为继血管病、癌症和脑卒中后的人类第四大杀手，老年抑郁症也开始增长。随着中国社会人口向老龄化发展，如何让老年人“心灵养老”也日益成为了社会的焦点。专家表示，应让“心灵养老”成为老年人的自觉行动，退休后的生活应保持与外界环境的接触，同时，应增进健康社交、培养兴趣、多动脑、多运动，懂得调节自我情绪，以此保持心理健康。',
  '交流分享',
  '2016-07-09',
  '888',
  '8',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '9', 
  '如何处理老年性痴呆患者情绪',
  '老年性痴呆患者经常会表现出忧郁、退缩及不高兴等情形，同时说话、行动和思考也变得迟缓。这些情形可能会影响患者的生活习惯以及对食物的兴趣
【建议】
•可以与医生商讨医生除了帮助你之外还可以向您介绍其他专业人员心理医师或精神科医生
•给患者更多的爱与支持
•不要期待患者的忧郁情形会马上好转',
  '护理须知',
  '2016-07-07',
  '666',
  '6',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '10', 
  '关于照顾老年性痴呆患者吃饭',
  '痴呆患者经常会忘了刚刚才吃过东西，或是忘了应如何使用餐具。对于晚期患者也许需要他人喂食。部分患者可能会因躯体疾病而出现无法咀嚼及吞咽的问题。
  【建议】
·要想方设法辅导病人如何进食
·准备些容易拿在手上让患者吃起来容易并且不会弄脏自己的食物
·将食物切割成小块，预防患者噎食。在痴呆晚期病人的食物可能需要加以磨碎或提供流质饮食
·提醒病人吃得慢点',
  '护理须知',
  '2016-07-13',
  '6131',
  '8',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '11', 
  '“老年痴呆症”治疗获重大突破',
  '据英国《每日快报》报道，阿尔茨海默症（也被称为“老年痴呆症”）治疗研究近日取得重大突破，一种能够有效阻止病情恶化的药物可能将在5年内面世。研究人员此前已经发现2型糖尿病和阿尔茨海默症之间存在联系。在一些疾病中，大脑无法正常利用糖，而糖代谢异常正是“元凶”之一。
一系列试验显示，利拉鲁肽这种价格低廉又常见的糖尿病药物，不仅能够阻止阿尔茨海默症的恶化，并且在一些情况下，还具有提振精神的作用。利拉鲁肽可适用于那些有患病风险的人，以及已经出现痴呆症状的患者，以维持他们正常的脑功能。',
  '临床新闻',
  '2016-07-07',
  '666',
  '6',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '12', 
  '报告称2020年中国老年痴呆症患者将超2000万',
  '近日，东中西部区域发展和改革研究院全球卫生与健康研究中心发布的卫生政策研究报告《从国家战略层面构建阿尔茨海默病长期照顾体系的建议》指出，全球有约3650万人患有老年痴呆症，每7秒就有一个人患上此病，平均生存期只有5.9年;中国作为世界上老年痴呆症患者最多的国家，2040年将达到2200万，是所有发达国家老年痴呆症患者数的总和。',
  '临床新闻',
  '2016-07-02',
  '4534',
  '7',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '13', 
  '科学家投入老年痴呆症转移性研究',
  '过去十年来，“流氓蛋白”——与阿尔茨海默氏症、帕金森氏症以及亨廷顿氏症等神经退行性疾病相关的淀粉样蛋白的统称——可能拥有与朊病毒类似的可转移性特征的证据日益增多。Collinge的证据也强化了这一观点。
  如果淀粉样蛋白可转移的假设被证实，其含义将极为深远。淀粉样蛋白会像胶水那样附着到金属手术器材上，常规消毒杀菌不能清除掉它们，因此淀粉样蛋白的种子可能在手术过程中发生转移。但这些种子在导致发病之前，可能会潜伏在体内数年甚至是数十年，并可能产生其他导致阿尔茨海默氏症的病变。如果脑血管中存在淀粉样蛋白，可能会带来其他危险，因为它们会增加血管壁破裂以及轻度中风的风险。',
  '研究前沿',
  '2016-04-07',
  '1265',
  '4',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);
INSERT INTO 'post' VALUES (
  '14', 
  '【医前沿】老年痴呆新药有望面世',
  '近日，刊登在国际顶级科学期刊、美国科学院院报《PNAS》上的一项研究成果让医学界振奋，这项由浙江大学医学院附属第二医院神经内科主任张宝荣教授研究团队、香港科技大学叶玉如院士领导的研究小组及英国格拉斯哥大学刘富友教授合作发现的成果，将有效改变老年痴呆治疗上的难题。
　　该研究成果称，一种叫白介素-33（IL-33）的蛋白质，成功使患阿尔兹海默症（AD，俗称老年痴呆）的转基因鼠，神经细胞通讯缺陷和记忆力衰退情况逆转。
　医学界普遍认为，要阻止老年痴呆病程的发展，需从临床早期，如轻度认知障碍（MCI）时期就开始干预。认知障碍症患者的特点是记忆力不佳，忘东忘西，日常生活能力也会降低，如热水应调多少度、煮菜放多少盐等判断丧失，影响到生活自理能力，“研究中我们发现，在风险较高的轻度认知障碍患者血清中，一种叫sST2的蛋白明显增高，而sST2是IL-33的诱饵受体，两者需要合作产生功能。原本用作调节免疫系统功能的IL-33蛋白功能异常，大脑清除淀粉样Aβ蛋白斑的功能就会下降。这提示着受损的IL-33/ST2信号通路可能在老年痴呆的发病机制中起着重要作用。”张宝荣说。',
  '研究前沿',
  '2016-05-10',
  '4364',
  '4',
  'F:\\MySQL\\image\\wwwtiefa-img_liaoliu.png'
);




-- ----------------------------
-- Table structure for favorites_post
-- ----------------------------
DROP TABLE IF EXISTS favorites_post;
CREATE TABLE favorites_post (
  user_id int(11) NOT NULL,
  post_id int(11) NOT NULL,
  PRIMARY KEY (user_id,post_id),
  KEY post_id (post_id),
  CONSTRAINT favorites_post_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (user_id),
  CONSTRAINT favorites_post_ibfk_2 FOREIGN KEY (post_id) REFERENCES post (post_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of favorites_post
-- ----------------------------
INSERT INTO 'favorites_post' VALUES ('1', '1');
INSERT INTO 'favorites_post' VALUES ('2', '1');



-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
  comment_id int(11) NOT NULL AUTO_INCREMENT,
  owner_id int(11) NOT NULL,
  post_id int(11) NOT NULL,
  comment_text varchar(400) NOT NULL,
  comment_date datetime NOT NULL,
  img_url varchar(255) NOT NULL,
  PRIMARY KEY (comment_id),
  KEY owner_id (owner_id),
  KEY post_id (post_id),
  CONSTRAINT comment_ibfk_1 FOREIGN KEY (owner_id) REFERENCES user (user_id),
  CONSTRAINT comment_ibfk_2 FOREIGN KEY (post_id) REFERENCES post (post_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO 'comment' VALUES ('1', '1'，'1', 
  '1.个性和人格改变。多数表现为自私、主观，或急躁易怒、不理智，或焦虑、多疑。还有一部分人表现为性格孤僻，以自我为中心，对周围事物不感兴趣，缺乏热情，与发病前相比判若两人。 
2.行动障碍。动作迟缓，走路不稳，偏瘫，甚至卧床不起，大小便失禁，不能自主时食，终至死亡是老年痴呆早期症状之一。',
  '2016-07-13',
  'F:\\1.png'
);
INSERT INTO 'comment' VALUES ('2', '5'，'1', 
  '老年性痴呆发病最初的症状是记忆障碍，老年痴呆早期症状主要表现为近期记忆的健忘，老年痴呆早期症状还有如同一内容无论向他述说几遍也会立即忘记，刚放置的东西就忘掉所放的位置，做菜时已放过盐过一会儿又放一次，刚买下的东西就忘记拿走，刚刚被介绍过的朋友，再次见面时就因忘了他的姓名而出现尴尬的场面。',
  '2016-07-13',
  'F:\\1.png'
);
INSERT INTO 'comment' VALUES ('3', '6'，'1', 
  '临床上人为的将AD的临床过程大致分为三个阶段。
  第一阶段(1-3年)：为轻度痴呆期。表现为记忆减退，对近事遗忘突出;判断能力下降，病人不能对事件进行分析、思考、判断，难以处理复杂的问题;工作或家务劳动漫不经心，不能独立进行购物、经济事务等，社交困难;尽管仍能做些已熟悉的日常工作，但对新的事物却表现出茫然难解，情感淡漠，偶尔激惹，常有多疑;出现时间定向障碍，对所处的场所和人物能作出定向，对所处地理位置定向困难，复杂结构的视空间能力差;言语词汇少，命名困难。
　第二阶段(2-10年)：为中度痴呆期。表现为远近记忆严重受损，简单结构的视空间能力下降，时间、地点定向障碍;在处理问题、辨别事物的相似点和差异点方面有严重损害;不能独立进行室外活动，在穿衣、个人卫生以及保持个人仪表方面需要帮助;计算不能;出现各种神经症状，可见失语、失用和失认;情感由淡漠变为急躁不安，常走动不停，可见尿失禁。
  第三阶段(8-12年)：为重度痴呆期。严重记忆力丧失，仅存片段的记忆;日常生活不能自理，大小便失禁，呈现缄默、肢体僵直，查体可见锥体束征阳性，有强握、摸索和吸吮等原始反射。最终昏迷，一般死于感染等并发症。',
  '2016-07-13',
  'F:\\1.png'
);
INSERT INTO 'comment' VALUES ('4', '3'，'1', 
  '多忘事，记不住东西吧。',
  '2016-07-13',
  'F:\\1.png'
);
INSERT INTO 'comment' VALUES ('5', '3'，'2', 
  '患者可能根本不知道自己在做什么行为的本身亦反应出他缺乏安全感问题的处理',
  '2016-07-13',
  'F:\\1.png'
);
INSERT INTO 'comment' VALUES ('6', '3'，'2', 
  '人家应该不知道他在做什么',
  '2016-07-13',
  'F:\\1.png'
);
INSERT INTO 'comment' VALUES ('7', '3'，'2', 
  '可能是忘了这件事情有没有做过吧。',
  '2016-07-13',
  'F:\\1.png'
);
INSERT INTO 'comment' VALUES ('7', '3'，'2', 
  '对此我们还是应该多多理解的，记忆力衰退得太快了' ,
  '2016-07-13',
  'F:\\1.png'
);
