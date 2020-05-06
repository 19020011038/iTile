package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.itile.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private String header;
    private String tou = "http://118.190.245.170";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        jiekou404(tou+"/worktile/404");
//        jiekou18(tou+"/worktile/task/3/subtask/3/members");
//        jiekou15(tou+"/worktile/task/3/subtask/3/state","0");
//        jiekou13(tou+"/worktile/task/3/subtask/3/manager","2");
//        jiekou10(tou+"/worktile/task/3/subtask/3/description","the fake last one");
//        jiekou9(tou+"/worktile/all-tasks");
//        jiekou8(tou+"/worktile/all-project");
//        jiekou6(tou+"/worktile/project/3/task/3/new-subtask","yyx","xixihaha","2020-04-30 08:55:00","2020-5-30 08:55:00");
//        jiekou5(tou+"/worktile/new-project","yyx","hahahaha");
//        jiekou4(tou+"/worktile/project/1");
//        getTask("http://118.190.245.170/worktile/project/1/task/1");
//        getTask("http://118.190.245.170/worktile/task/1/subtask/1");
//        getWork("http://118.190.245.170//worktile/work");
//        getTest("http://118.190.245.170//worktile/test");
    }
    public void jiekou404(String address) {
        HttpUtil.jiekou404(address,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                if("".equals(responseData))
                    Log.d("response是空的！",tou);
                Log.d("responseData：", responseData);

                try {
                    JSONObject jsonObject  = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void jiekou18(String address) {
        HttpUtil.jiekou18(address,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);

                try {
                    JSONObject jsonObject  = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void jiekou15(String address,String state) {
        HttpUtil.jiekou15(address,state,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);

                try {
                    JSONObject jsonObject  = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void jiekou10(String address,String description) {
        HttpUtil.jiekou10(address,description,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);

                try {
                    JSONObject jsonObject  = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void jiekou13(String address,String manager_id) {
        HttpUtil.jiekou13(address,manager_id,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);

                try {
                    JSONObject jsonObject  = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void jiekou9(String address) {
        HttpUtil.jiekou9(address,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);

                try {
                    JSONObject jsonObject  = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void jiekou8(String address) {
        HttpUtil.jiekou8(address,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);

                try {
                    JSONArray jsonArray  = new JSONArray(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void jiekou6(String address,String name,String description,String starttime,String endtime) {
        HttpUtil.jiekou6(address,name,description, starttime,endtime,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);

                try {
                    JSONObject jsonObject  = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void jiekou5(String address,String name,String description) {
        HttpUtil.jiekou5(address,name,description, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);

                try {
                    JSONObject jsonObject  = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void jiekou4(String address) {
        HttpUtil.jiekou4(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);

                try {
                    JSONObject jsonObject  = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getProject(String address) {
        HttpUtil.getProject(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
//                    }
//                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);
//                header = response.header("set-cookie");
//                Log.d("header",header);
                try {
                    JSONObject jsonObject  = new JSONObject(responseData);

//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                    Log.d("第一个",String.valueOf(jsonObject));


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }


    public void getTask(String address) {
        HttpUtil.getTask(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
//                    }
//                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);
//                header = response.header("set-cookie");
//                Log.d("header",header);
                try {
                    JSONObject jsonObject  = new JSONObject(responseData);

//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                    Log.d("第一个",String.valueOf(jsonObject));


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }

    public void getTest(String address) {
        HttpUtil.getTest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
//                    }
//                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);
//                header = response.header("set-cookie");
//                Log.d("header",header);
                try {
                    JSONArray jsonArray = new JSONArray(responseData);
//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                    Log.d("第一个",String.valueOf(jsonObject));


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }
    public void getWork(String address) {
        HttpUtil.getWork(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
//                    }
//                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("responseData：", responseData);
//                header = response.header("set-cookie");
//                Log.d("header",header);
                try {
                    JSONArray jsonArray = new JSONArray(responseData);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Log.d("第一个",String.valueOf(jsonObject));
                    String id = jsonObject.getString("id");
                    Log.d("id",id);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }
}
