package cn.imrhj.cowlevel.network.model.feed

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.ContentModel

/**
 * Created by rhj on 2017/12/4.
 * id : 1827666
 * title : 迟到3年的评测-精品王道JRPG《勇气默示录》
 * comment_count : 7
 * vote_count : 17
 * pic : https://pic1.cdncl.net/game/user_upload/acaciaforjesus/8660dbd773acb93ce08722f8d86ec053.jpg
 * has_vote : 0
 * has_collect : 0
 * brief_content : {"desc":"&nbsp;游戏：《勇气默示录-飞翔妖精》完全版（勇气原点/BRAVELY DEFAULT For the Sequel） &nbsp;平台：3DS &nbsp;发售时间：2013年12月5日 【前言】 最初接触和这个作 ...","thumb":"http://a4.qpic.cn/psb?/V13VV3Bg41hef5/PcP2QZMNTwtcBJqiVFslc7x6bq.H.1dSed9lqq1uIjo!/b/dAcBAAAAAAAA&amp;ek=1&amp;kp=1&amp;pt=0&amp;bo=wAMABQAAAAAAAOA!&amp;t=5&amp;su=56619313&amp;sce=0-12-12&amp;rf=2-9&utm_source=cowlevel"}
 * neat_content : {"desc":"&nbsp;游戏：《勇气默示录-飞翔妖精》完全版（勇气原点/BRAVELY DEFAULT For the Sequel） &nbsp;平台：3DS &nbsp;发售时间：2013年12月5日 【前言】 最初接触和这个作 ...","thumb":"http://a4.qpic.cn/psb?/V13VV3Bg41hef5/PcP2QZMNTwtcBJqiVFslc7x6bq.H.1dSed9lqq1uIjo!/b/dAcBAAAAAAAA&amp;ek=1&amp;kp=1&amp;pt=0&amp;bo=wAMABQAAAAAAAOA!&amp;t=5&amp;su=56619313&amp;sce=0-12-12&amp;rf=2-9&utm_source=cowlevel"}
 * hide_comment : 0
 * lock_comment : 0
 */
data class FeedArticleModel(
        val id: Int = 0,
        val title: String? = null,
        val comment_count: Int = 0,
        val vote_count: Int = 0,
        val pic: String? = null,
        val has_vote: Int = 0,
        val has_collect: Int = 0,
        val brief_content: ContentModel? = null,
        val neat_content: ContentModel? = null,
        val hide_comment: Int = 0,
        val lock_comment: Int = 0
) : BaseModel()
