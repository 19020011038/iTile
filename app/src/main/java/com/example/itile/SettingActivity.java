package com.example.itile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.itile.Util.HttpUtil;
import com.example.itile.Util.SharedPreferencesUtil;
import com.longsh.optionframelibrary.OptionBottomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferencesUtil check;
    private ImageView setting_icon;
    private RelativeLayout icon;
    private RelativeLayout change_sex;
    private RelativeLayout change_work;
    private Button setting_signout;
    private String result;
    private String icon_string;
    private TextView nickname;
    private TextView sex;
    private TextView work;
    private TextView birthday;
    private TextView intro;
    private int lastChoose_sex=0;
    private int lastChoose_work=0;
    private String string_choose;
    private int now_year = 2020; //今日日期
    private int now_month = 5;
    private int now_day = 27;
    private int year = 2000;
    private int month = 1;
    private int day = 1;
    private NumberPicker numberpicker0;
    // numberpicker2,numberpicker3是星座month和day
    private NumberPicker numberpicker2;
    private NumberPicker numberpicker3;
    private NumberPicker np;
    private TextView text;
    private TextView text1;
    private int num;
    String cityy;
    private String[] city= {"北京","上海","广州","深圳","成都","天津"};
    private Button bt1;
    private Button bt2;
    private android.app.AlertDialog.Builder dialog;
    private android.app.AlertDialog.Builder dialog2;
    private TextView tv1;
    private TextView tv2;
    private View inputServer2;
    private EditText edit;
    private Bitmap bitmap;

    //修改性别相关
    private String[] areas_sex = new String[]{"男","女"};
    private RadioOnClick_sex radioOnClick_sex = new RadioOnClick_sex(0);
    private ListView areaRadioListView;

    //修改职业相关
//    private String[] areas_work = new String[]{"IT（计算机/互联网/通信）", "制造（生产/工艺/制造）", "医疗（医疗/护理/制药）", "金融（金融/银行/投资/保险）", "商业（商业/服务业/个体经营）", "文化（文化/广告/传媒）", "艺术（娱乐/艺术/表演）","法律（律师/法务）", "教育（教育/培训）",
//            "行政（公务员/行政/事业单位）", "模特（模特）","空姐（空姐）","学生（学生）", "其他职业"};
    private String[] areas_work = new String[]{"暂不填写","IT", "制造", "医疗", "金融", "商业", "文化", "艺术","法律", "教育",
            "行政", "模特","空姐","学生", "其他职业"};
    private String[] work_list = new String[]{"未填写","IT", "制造", "医疗", "金融", "商业", "文化", "艺术","法律", "教育", "行政", "模特","空姐","学生", "其他"};
    private RadioOnClick_work radioOnClick_work = new RadioOnClick_work(0);

    private byte[] a = null;

    //拍照功能参数
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private final static int CROP_IMAGE = 3;

    //imageUri照片真实路径
    private Uri imageUri;
    //照片存储
    File filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);

        //别忘了这句！！！！
        check = SharedPreferencesUtil.getInstance(getApplicationContext());

        setting_icon = findViewById(R.id.setting_icon);
        setting_signout = findViewById(R.id.setting_signout);
        icon = findViewById(R.id.icon);
        nickname = findViewById(R.id.nickname);
        sex = findViewById(R.id.sex);
        work = findViewById(R.id.work);
        birthday = findViewById(R.id.birthday);
        change_sex = findViewById(R.id.setting_change_sex);
        change_work = findViewById(R.id.setting_change_work);
        intro = findViewById(R.id.intro);

        //修改简介
        LayoutInflater factory = LayoutInflater.from(SettingActivity.this);//提示框
        inputServer2 = factory.inflate(R.layout.item_setting_intro, null);//这里必须是final的
        edit=(EditText)inputServer2.findViewById(R.id.intro);//获得输入框对象
        edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i=s.length();i>0;i--){
                    if(s.subSequence(i-1,i).toString().equals("\n"))
                        s.replace(i-1,i,"");
                }
            }
        });

        //修改性别
        radioOnClick_sex.setIndex(0);
        change_sex.setOnClickListener(new RadioClickListener_sex());
        //修改职业
        radioOnClick_work.setIndex(0);
        change_work.setOnClickListener(new RadioClickListener_work());

        setting_signout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("zyr","setting_isLogin:"+check.isLogin());
                signoutWithOkHttp("http://118.190.245.170/worktile/userinfo/");
//                signoutWithOkHttp("http://175.24.47.150:8088/worktile/userinfo/");

            }
        });
//        homeNameOkHttp("http://47.102.46.161/worktile/userinfo");
//        http://175.24.47.150:8088/worktile/static/
//        homeNameOkHttp("http://175.24.47.150:8088/worktile/userinfo/");
        homeNameOkHttp("http://118.190.245.170/worktile/userinfo/");
    }

    @Override
    protected void onResume() {
        super.onResume();

        homeNameOkHttp("http://118.190.245.170/worktile/userinfo/");
        radioOnClick_sex.setIndex(lastChoose_sex);
        radioOnClick_work.setIndex(lastChoose_work);
//        //别忘了这句！！！！
//        check = SharedPreferencesUtil.getInstance(getApplicationContext());
//
//        setting_icon = findViewById(R.id.setting_icon);
//        setting_signout = findViewById(R.id.setting_signout);
//
//        setting_signout.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Log.i("zyr","setting_isLogin:"+check.isLogin());
////                signoutWithOkHttp("http://47.102.46.161/user/logout");
//
//            }
//        });
////        homeNameOkHttp("http://47.102.46.161/worktile/userinfo");
////        http://175.24.47.150:8088/worktile/static/
//        homeNameOkHttp("http://175.24.47.150:8088/worktile/userinfo/");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_change_nickname:  //修改昵称
                Intent intent2 = new Intent(SettingActivity.this, ChangeNicknameActivity.class);
                startActivity(intent2);
                break;
            case R.id.setting_change_password:  //修改密码
                Intent intent1 = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.setting_back:
                finish();
                break;
//            case R.id.setting_change_sex:  //修改性别
//
//                break;
//            case R.id.setting_change_work:  //修改性别
//
//                break;
            case R.id.setting_change_birthday:  //修改生日
                dialog = createLoadingDialog(SettingActivity.this, "test");
                dialog.create().show();
                break;
            case R.id.setting_change_intro:
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
//                builder.setCancelable(false);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        ((ViewGroup) inputServer2.getParent()).removeView(inputServer2);
                    }
                });
//                builder.setTitle("个性点的签名").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                builder.setTitle("修改简介").setView(inputServer2)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                ((ViewGroup) inputServer.getParent()).removeView(inputServer);
                            }
                        });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String _sign = edit.getText().toString();
                        SettingWithOkhttp("introduction", _sign);
//                        ((ViewGroup) inputServer.getParent()).removeView(inputServer);

//                        if(_sign!=null && !_sign.isEmpty())
//                        {
//                            textView.setText(_sign);
//                        }
//                        else
//                        {
//                            Toast.makeText(SettingActivity.this,"签名为空",Toast.LENGTH_SHORT).show();
//                        }
                    }
                });
                builder.show();
                break;
            case R.id.icon:

                AlertDialog.Builder dialog = new AlertDialog.Builder (SettingActivity.this);
                dialog.setTitle("是否更改头像？");
                dialog.setMessage("若更改头像请点击确定");
                dialog.setCancelable(false);
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(Home.this, "相机相机相册相册", Toast.LENGTH_SHORT).show();
                        List<String> stringList = new ArrayList<String>();
                        stringList.add("拍照");
                        stringList.add("从相册选择");
                        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(SettingActivity.this, stringList);
                        optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {


                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //取消底部弹框
                                //optionBottomDialog.dismiss();
                                switch (position) {
                                    case 0:
                                        //测试使用，验证是否为position= 0
                                        //启动相机程序
                                        //隐式Intent
                                        Intent intent_photo = new Intent( "android.media.action.IMAGE_CAPTURE" );
                                        //putExtra()指定图片的输出地址，填入之前获得的Uri对象
                                        imageUri = SettingActivity.ImageUtils.getImageUri( SettingActivity.this );
                                        intent_photo.putExtra( MediaStore.EXTRA_OUTPUT, imageUri );
                                        //startActivity( intent_photo );
                                        startActivityForResult( intent_photo, TAKE_PHOTO );
                                        //底部弹框消失
                                        optionBottomDialog.dismiss();
                                        break;
                                    case 1:
                                        //测试使用，验证是否为position= 1
                                        //Toast.makeText(RegisterIn.this,"position 1", Toast.LENGTH_SHORT ).show();
                                        //打开相册
                                        openAlbum();
                                        //底部弹框消失
                                        optionBottomDialog.dismiss();
                                        break;
                                    default:
                                        break;
                                }
                            }
                            //写到这了

                        });

                    }
                });
                dialog.show();
                break;
        }
    }


    //又在这开始写了
    //打开相册
    private void openAlbum() {
        Intent intent_album = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );

        startActivityForResult( intent_album, CHOOSE_PHOTO );
    }

    //剪切图片
    private void startImageCrop(File saveToFile,Uri uri) {
        if(uri == null){
            return ;
        }
        Intent intent = new Intent( "com.android.camera.action.CROP" );
        Log.i( "Test", "startImageCrop: " + "执行到压缩图片了" + "uri is " + uri );
        intent.setDataAndType( uri, "image/*" );//设置Uri及类型
        //uri权限，如果不加的话，   会产生无法加载图片的问题
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra( "crop", "true" );//
        intent.putExtra( "aspectX", 1 );//X方向上的比例
        intent.putExtra( "aspectY", 1 );//Y方向上的比例
        intent.putExtra( "outputX", 250 );//裁剪区的X方向宽
        intent.putExtra( "outputY", 250 );//裁剪区的Y方向宽
        intent.putExtra( "scale", true );//是否保留比例
        intent.putExtra( "outputFormat", Bitmap.CompressFormat.PNG.toString() );
        intent.putExtra( "return-data", false );//是否将数据保留在Bitmap中返回dataParcelable相应的Bitmap数据，防止造成OOM，置位false
        //判断文件是否存在
        //File saveToFile = ImageUtils.getTempFile();
        if (!saveToFile.getParentFile().exists()) {
            saveToFile.getParentFile().mkdirs();
        }
        //将剪切后的图片存储到此文件
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveToFile));
        Log.i( "Test", "startImageCrop: " + "即将跳到剪切图片" );
        startActivityForResult( intent, CROP_IMAGE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //需要对拍摄的照片进行处理编辑
                    //拍照成功的话，就调用BitmapFactory的decodeStream()方法把图片解析成Bitmap对象，然后显示
                    Log.i("Test", "onActivityResult TakePhoto : " + imageUri);
                    //Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
                    //takephoto.setImageBitmap( bitmap );
                    //设置照片存储文件及剪切图片
                    File saveFile = SettingActivity.ImageUtils.getTempFile();
                    filePath = SettingActivity.ImageUtils.getTempFile();
                    startImageCrop(saveFile, imageUri);
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //选中相册照片显示
                    Log.i("Test", "onActivityResult: 执行到打开相册了");
                    try {
                        imageUri = data.getData(); //获取系统返回的照片的Uri
                        Log.i("Test", "onActivityResult: uriImage is " + imageUri);
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(imageUri,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        //                        photo_taken.setImageBitmap(bitmap);
                        //设置照片存储文件及剪切图片
                        File saveFile = SettingActivity.ImageUtils.setTempFile(SettingActivity.this);
                        filePath = SettingActivity.ImageUtils.getTempFile();
                        startImageCrop(saveFile, imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CROP_IMAGE:
                if (resultCode == RESULT_OK) {
                    Log.i("Test", "onActivityResult: CROP_IMAGE" + "进入了CROP");
                    // 通过图片URI拿到剪切图片
                    //bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
                    //通过FileName拿到图片
                    Bitmap bitmap1 = BitmapFactory.decodeFile(filePath.toString());


                    //把裁剪后的图片展示出来
//                    setting_icon.setImageBitmap(bitmap);
                    bitmap = compressImage(bitmap1);
                    File file = getFile(bitmap);
                    Log.i("zyr", file.toString());
                    iconWithOkHttp("http://118.190.245.170/worktile/userinfo/", filePath);
//                    iconWithOkHttp("http://175.24.47.150:8088/worktile/userinfo/", filePath);
                    //a = getBitmapByte(bitmap1);
//                    MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(Home.this);
//                    SQLiteDatabase database = myDataBaseHelper.getReadableDatabase();
//                    ContentValues values = new ContentValues();
//                    values.put("icon", a);
//                    database.update("user", values, "id=?", new String[]{user_id});
//                    database.close();
//                    String b = a.toString();
                    //Log.i( "zyr", "b" );

//                    Intent intent990 = new Intent(Home.this, Main.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent990);
                }
                break;
            default:
                break;
        }
    }

    public static class ImageUtils {
        private static String TAG = "Test";
        public static File tempFile;

        public static Uri getImageUri(Context content) {
            File file = setTempFile( content );
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(Build.VERSION.SDK_INT >= 24){
                //将File对象转换成封装过的Uri对象，这个Uri对象标志着照片的真实路径
                Uri imageUri = FileProvider.getUriForFile( content, "com.example.itile.fileprovider", file );
                return imageUri;                                                          //a15927.bottombardemo
            }else{
                //将File对象转换成Uri对象，这个Uri对象标志着照片的真实路径
                Uri imageUri = Uri.fromFile( file );
                return imageUri;
            }
        }

        public static File setTempFile(Context content) {
            //自定义图片名称
            String name = DateFormat.format("yyyyMMdd_hhmmss", SettingActivity.ImageUtils.Calendar.getInstance( Locale.CHINA)) + ".png";
            Log.i( TAG, " name : "+name );
            //定义图片存放的位置
            tempFile = new File(content.getExternalCacheDir(),name);
            Log.i( TAG, " tempFile : "+tempFile );
            return tempFile;
        }

        public static File getTempFile() {
            return tempFile;
        }

        private static class Calendar {
            public static long getInstance(Locale china) {
                return 0;
            }
        }
    }

    //对bitmap进行质量压缩
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    //在这里抽取了一个方法   可以封装到自己的工具类中...
    //bitmap转化成file
    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //修改头像
    public void iconWithOkHttp(String address, File icon_file){
        HttpUtil.userIconWithOkHttp(address, icon_file, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " settingActivity : icon_error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
//
                try{
                    JSONObject object = new JSONObject(responseData);
                    result = object.getString("warning");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                Toast.makeText(SettingActivity.this, "头像修改成功", Toast.LENGTH_SHORT).show();
                                setting_icon.setImageBitmap(bitmap);
                            } else if (result != null) {
                                Toast.makeText(SettingActivity.this, result, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SettingActivity.this, "头像修改失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }//标签页
        });
    }

    //退出登录
    public void signoutWithOkHttp(String address){
        HttpUtil.signoutWithOkhttp(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " signoutActivity : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
//                final String responseData = response.body().string();

//                    JSONObject object = new JSONObject(responseData);
//                    result = object.getString("warning");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                            Toast.makeText(SettingActivity.this,"退出登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SettingActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            check.setLogin(false);
                            startActivity(intent);
                            finish();
                    }
                });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.i( "zyr", "LLL"+"error");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(SettingActivity.this,"退出登录失败",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
            }//标签页
        });
    }
    //展示信息
    public void homeNameOkHttp(String address){
        HttpUtil.homeNameOkHttp(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try{
                    JSONObject object = new JSONObject(responseData);
                    JSONObject object1 = object.getJSONObject("user");
                    String s_nickname=object1.getString("username");
                    icon_string = object1.getString("avatar");
                    String constellation = object1.getString("constellation");//星座
                    String profession = object1.getString("profession"); //专业
                    String age = object1.getString("age");//年龄
                    String gender = object1.getString("gender");//性别
                    String introduction = object1.getString("introduction");//简介
                    JSONObject birthday_s = object1.getJSONObject("birthday");
                    String year_s = birthday_s.getString("year");
                    String month_s = birthday_s.getString("month");
                    String day_s = birthday_s.getString("day");
                    JSONObject now_s = object1.getJSONObject("now");
                    String year_now = now_s.getString("year");
                    String month_now = now_s.getString("month");
                    String day_now = now_s.getString("day");
                    Log.i("zyr", "HomeActivity.icon_url:"+icon_string);
                    now_year = Integer.valueOf(year_now);
                    now_month = Integer.valueOf(month_now);
                    now_day = Integer.valueOf(day_now);
                    if (!year_s.equals("null"))
                        year = Integer.valueOf(year_s);
                    if (!month_s.equals("null"))
                        month = Integer.valueOf(month_s);
                    if (!day_s.equals("null"))
                        day = Integer.valueOf(day_s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String birthday_all = year_s+"-"+month_s+"-"+day_s;
                        nickname.setText(s_nickname);
                        if (!gender.equals("null")) {
                            sex.setText(gender);
                            if(gender.equals("女")) {
                                lastChoose_sex = 1;
                                radioOnClick_sex.setIndex(lastChoose_sex);
                            }else{
                                lastChoose_sex=0;
                                radioOnClick_sex.setIndex(lastChoose_sex);
                            }
                        }else {
                            sex.setText("男");
                            lastChoose_sex=0;
                            radioOnClick_sex.setIndex(lastChoose_sex);
                        }
                        if (!profession.equals("null")) {
                            work.setText(profession);
                            for (int i=0;i<14;i++)
                                if (profession.equals(work_list[i])) {
                                    lastChoose_work = i;
                                    radioOnClick_work.setIndex(lastChoose_work);
                                    break;
                                }
                        }
                        else
                            work.setText("未填写");
                        if (!year_s.equals("null"))
                            birthday.setText(birthday_all);
                        else
                            birthday.setText("未填写");
                        if (!introduction.equals("null")) {
                            intro.setText(introduction);
                            edit.setText(introduction);
                        }
                        else
                            intro.setText("未填写");
                        Glide.with(SettingActivity.this).load("http://118.190.245.170/worktile/media/"+icon_string).into(setting_icon);
//                        Toast.makeText(HomeActivity.this,"显示头像",Toast.LENGTH_SHORT).show();
//                        Glide.with(SettingActivity.this).load("http://175.24.47.150:8088/worktile/static/"+icon_string).into(setting_icon);
                    }
                });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                    Toast.makeText(SettingActivity.this, "服务器连接错误", Toast.LENGTH_SHORT).show();
                }
            }//标签页
        });
    }

    //修改性别相关
    class RadioClickListener_sex implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog ad =new AlertDialog.Builder(SettingActivity.this).setTitle("修改性别").setSingleChoiceItems(areas_sex,lastChoose_sex,radioOnClick_sex).create();
            areaRadioListView=ad.getListView();
            ad.show();
        }
    }
    /**
     * 点击单选框事件
     * @author xmz
     *
     */
    //修改性别相关
    class RadioOnClick_sex implements DialogInterface.OnClickListener{
        private int index;

        public RadioOnClick_sex(int index){
            this.index = index;
        }
        public void setIndex(int index){
            this.index=index;
        }
        public int getIndex(){
            return index;
        }
        public String getStringIndex(){
            if (index==1)
                return "女";
            else
                return "男";
        }
        public void onClick(DialogInterface dialog, int whichButton){
            setIndex(whichButton);

            SettingWithOkhttp("gender",radioOnClick_sex.getStringIndex());
            dialog.dismiss();
        }
    }

    //修改职业相关
    class RadioClickListener_work implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog ad =new AlertDialog.Builder(SettingActivity.this).setTitle("选择职业").setSingleChoiceItems(areas_work,lastChoose_work,radioOnClick_work).create();
            areaRadioListView=ad.getListView();
            ad.show();
        }
    }
    /**
     * 点击单选框事件
     * @author xmz
     *
     */
    //修改职业相关
    class RadioOnClick_work implements DialogInterface.OnClickListener{
        private int index;

        public RadioOnClick_work(int index){
            this.index = index;
        }
        public void setIndex(int index){
            this.index=index;
        }
        public int getIndex(){
            return index;
        }
        public String getStringIndex(){
            return work_list[index];
        }
        public void onClick(DialogInterface dialog, int whichButton){
            setIndex(whichButton);
            SettingWithOkhttp("profession",radioOnClick_work.getStringIndex());
            dialog.dismiss();
        }
    }


    //修改相关
    public void SettingWithOkhttp(String object_string, String value){
        HttpUtil.SettingWithOkHttp( object_string,value, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseData);
                    String result = object.getString("warning");
                    Log.i("zyr", "response:"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")){
                                if (object_string.equals("gender")) {
                                    lastChoose_sex = radioOnClick_sex.getIndex();
                                    if (lastChoose_sex == 0)
                                        sex.setText("男");
                                    else
                                        sex.setText("女");
                                    Toast.makeText(SettingActivity.this, "性别修改成功", Toast.LENGTH_SHORT).show();
                                }else if (object_string.equals("profession")){
                                    lastChoose_work = radioOnClick_work.getIndex();
                                    work.setText(work_list[lastChoose_work]);
                                    Toast.makeText(SettingActivity.this, "职业修改成功", Toast.LENGTH_SHORT).show();
                                }else if(object_string.equals("birthday")){
                                    Toast.makeText(SettingActivity.this, "生日修改成功", Toast.LENGTH_SHORT).show();
                                    birthday.setText(year+"-"+month+"-"+day);
                                }else if (object_string.equals("introduction")){
                                    intro.setText(value);
                                    Toast.makeText(SettingActivity.this, "简介修改成功", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(SettingActivity.this, "修改失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("zyr", "LLL" + responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingActivity.this, "无法连接到服务器", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }//标签页
        });
    }

    //下面是修改生日相关
    public android.app.AlertDialog.Builder createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_birthday, null);// 得到加载view

        numberpicker0 = (NumberPicker) v.findViewById(R.id.numberpicker1);
        numberpicker0.setMaxValue(now_year);
        numberpicker0.setMinValue(1900);
        numberpicker0.setValue(year);
        numberpicker0.setFocusable(true);
        numberpicker0.setFocusableInTouchMode(true);
        numberpicker0.setOnValueChangedListener(yearChangedListener);

        numberpicker2 = (NumberPicker) v.findViewById(R.id.numberpicker2);
        numberpicker2.setMaxValue(12);
        numberpicker2.setMinValue(1);
        numberpicker2.setValue(month);
        numberpicker2.setFocusable(true);
        numberpicker2.setFocusableInTouchMode(true);
        numberpicker2.setOnValueChangedListener(monthChangedListener);

        /*
         * / setMaxValue根据每月的天数不一样，使用switch()进行分别判断
         */
        numberpicker3 = (NumberPicker) v.findViewById(R.id.numberpicker3);
        numberpicker3.setMinValue(1);
        numberpicker3.setMaxValue(31);
        numberpicker3.setValue(day);
        numberpicker3.setFocusable(true);
        numberpicker3.setFocusableInTouchMode(true);
        numberpicker3.setOnValueChangedListener(dayChangedListener);
        text1 = (TextView) v.findViewById(R.id.textxing);

        xingzuo();

        android.app.AlertDialog.Builder loadingDialog = new android.app.AlertDialog.Builder(context);
        loadingDialog.setMessage("生日");
        loadingDialog.setView(v);
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
//                        tv1.setText(year+"年"+month + "月" + day + "日" +"\t" + text1.getText().toString());
//                        tv1.setText(month + "月" + day + "日");
                        SettingWithOkhttp("birthday", year+"-"+month+"-"+day+"-"+text1.getText().toString());
                    }
                });
        loadingDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        return loadingDialog;
    }

    private NumberPicker.OnValueChangeListener yearChangedListener = new NumberPicker.OnValueChangeListener() {

        @Override
        public void onValueChange(NumberPicker arg0, int arg1, int arg2) {

            year = numberpicker0.getValue();
            month = numberpicker2.getValue();
            xingzuo();
            if (year==now_year)
                numberpicker2.setMaxValue(now_month);
            else
                numberpicker2.setMaxValue(12);
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    numberpicker3.setMaxValue(31);
                    break;
                case 2:
                    if ((year%4==0&&year%100!=0)|| year%400==0)
                        numberpicker3.setMaxValue(29);
                    else
                        numberpicker3.setMaxValue(28);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    numberpicker3.setMaxValue(30);
                    break;

                default:
                    break;
            }
            if (year==now_year&& month==now_month)
                numberpicker3.setMaxValue(now_day);
            // TODO Auto-generated method stub
//            year = numberpicker0.getValue();
//            if (year%)
//            xingzuo();
//            switch (month) {
//                case 1:
//                case 3:
//                case 5:
//                case 7:
//                case 8:
//                case 10:
//                case 12:
//                    numberpicker3.setMaxValue(31);
//                    break;
//                case 2:
//                    numberpicker3.setMaxValue(29);
//                    break;
//                case 4:
//                case 6:
//                case 9:
//                case 11:
//                    numberpicker3.setMaxValue(30);
//                    break;
//
//                default:
//                    break;
//            }
        }

    };


    private NumberPicker.OnValueChangeListener monthChangedListener = new NumberPicker.OnValueChangeListener() {

        @Override
        public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
            year = numberpicker0.getValue();
            month = numberpicker2.getValue();
            xingzuo();

            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    numberpicker3.setMaxValue(31);
                    break;
                case 2:
                    if ((year%4==0 && year%100!=0)|| year%400==0)
                        numberpicker3.setMaxValue(29);
                    else
                        numberpicker3.setMaxValue(28);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    numberpicker3.setMaxValue(30);
                    break;

                default:
                    break;
            }
            if (year==now_year&& month==now_month)
                numberpicker3.setMaxValue(now_day);
        }

    };

    private NumberPicker.OnValueChangeListener dayChangedListener = new NumberPicker.OnValueChangeListener() {

        @Override
        public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
            year = numberpicker0.getValue();
            month = numberpicker2.getValue();
            day = numberpicker3.getValue();
            xingzuo();
            if (year==now_year&& month==now_month)
                numberpicker3.setMaxValue(now_day);
        }

    };

    // 对获得的month 和 day 进行处理
    void xingzuo() {

        if ((month == 1 && day > 19) || (month == 2 && day < 19)) {
            text1.setText("水瓶座");
        } else if ((month == 2 && day > 18) || (month == 3 && day < 21)) {
            text1.setText("双鱼座");
        } else if ((month == 3 && day > 20) || (month == 4 && day < 20)) {
            text1.setText("白羊座");
        } else if ((month == 4 && day > 19) || (month == 5 && day < 21)) {
            text1.setText("金牛座");
        } else if ((month == 5 && day > 20) || (month == 6 && day < 22)) {
            text1.setText("双子座");
        } else if ((month == 6 && day > 21) || (month == 7 && day < 23)) {
            text1.setText("巨蟹座");
        } else if ((month == 7 && day > 22) || (month == 8 && day < 23)) {
            text1.setText("狮子座");
        } else if ((month == 8 && day > 22) || (month == 9 && day < 23)) {
            text1.setText("处女座");
        } else if ((month == 9 && day > 22) || (month == 10 && day < 24)) {
            text1.setText("天秤座");
        } else if ((month == 10 && day > 23) || (month == 11 && day < 23)) {
            text1.setText("天蝎座");
        } else if ((month == 11 && day > 22) || (month == 12 && day < 22)) {
            text1.setText("射手座");
        } else if ((month == 12 && day > 21) || (month == 1 && day < 20)) {
            text1.setText("摩羯座");
        }

    }
}
