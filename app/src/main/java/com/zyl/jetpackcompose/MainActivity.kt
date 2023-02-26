package com.zyl.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zyl.jetpackcompose.ui.theme.JetpackcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackcomposeTheme() {
                Conversation(messages = SampleData.conversationSample)


//                Surface(modifier = Modifier.fillMaxSize()) {
//                    MessageCard(Message("Android","Jetpack Compose"))
//                }


            }

        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    //消息周围添加填充
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                //将图像大小设为40dp
                .size(40.dp)
                //剪辑要塑造为圆形的图像
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        //在图像和列之前添加水平间距
        Spacer(modifier = Modifier.width(8.dp))


        //跟踪消息是否展开
        var isExpanded by remember {
            mutableStateOf(false)
        }
        
        //表面颜色从一种颜色换到另一种颜色
        val surfaceColor by animateColorAsState(
            targetValue = if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        
        
        //点击此列时，就切换 isExpanded 的变量

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            //在头像和消息文本之间添加垂直空格
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                //表面颜色从 Primary 变成 surfa
                color = surfaceColor,
                //逐渐改变大小
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    //如果展开，就显示全部内容，否则只显示第一行
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.subtitle2
                )
            }

        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

//@Preview(name = "Light Mode")
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Dark Mode"
//)
//@Composable
//fun PreviewMessageCard() {
//    JetpackcomposeTheme() {
//        Surface() {
//            MessageCard(
//                msg = Message("colleague", "Hey, take a look at Jetpack Compose")
//            )
//        }
//    }
//
//}
@Preview
@Composable
fun PreviewConversation() {
    JetpackcomposeTheme() {
        Conversation(messages = SampleData.conversationSample)

    }
}