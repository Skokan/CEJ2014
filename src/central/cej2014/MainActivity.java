package central.cej2014;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	private final String home = "http://www.cej2014.cz";
	private final String generalInfo = "http://www.cej2014.cz/general-info.html";
	private final String participants = "http://www.cej2014.cz/participants.html";
	private final String ist = "http://www.cej2014.cz/ist.html";
	private WebView web;
	public View wascklicked;
	int currentapiVersion = android.os.Build.VERSION.SDK_INT;
	private int px;
	private Button bt1;
	private Button bt2;
	private ImageView im1;
	private WebViewClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		web = (WebView) findViewById(R.id.webView1);

		client = new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Uri i = Uri.parse(url);
				try {
					List<String> params = i.getPathSegments();
					String first = params.get(0);
					if (first.equals("general-info.html")) {
						View v = findViewById(R.id.button1);
						vyberTlacitka(v);
					} else if (first.equals("participants.html")) {
						View v = findViewById(R.id.button2);
						vyberTlacitka(v);
					}
				} catch (Exception e) {
					View v = findViewById(R.id.imageView1);
					vyberTlacitka(v);
				}
				super.onPageStarted(view, url, favicon);
			}

		};

		web.setWebViewClient(client);

		try {
			String data = getIntent().getData().toString();
			web.loadUrl(data);
		} catch (Exception e) {
			web.loadUrl(home);
		}


		bt1 = (Button) findViewById(R.id.button1);
		bt2 = (Button) findViewById(R.id.button2);
		im1 = (ImageView) findViewById(R.id.imageView1);

		wascklicked = findViewById(R.id.imageView1);

		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		im1.setOnClickListener(this);

		Resources r = this.getResources();
		px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
				r.getDisplayMetrics());

	}

	public void prekresli(int id, Button v, int color) {
		Drawable dr = getResources().getDrawable(id);
		if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			v.setBackground(dr);
		} else {
			v.setBackgroundResource(id);
		}
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v
				.getLayoutParams();
		params.leftMargin = 2 * px;
		v.setTextColor(color);

		int lMargin = 16 * px;
		int tMargin = 4 * px;
		int rMargin = 8 * px;
		v.setPadding(lMargin, tMargin, rMargin, 0);
		v.setLayoutParams(params);
		v.requestLayout();

	}

	public void vyberTlacitka(View arg0) {
		if (wascklicked.getId() != R.id.imageView1) {
			prekresli(R.drawable.button, (Button) wascklicked, 0xffffffff);
		}
		wascklicked = arg0;
		if (arg0.getId() != R.id.imageView1) {
			prekresli(R.drawable.button_active, (Button) arg0, 0xff003f66);
		}

	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
		case R.id.button1:
			web.loadUrl(generalInfo);
			break;
		case R.id.button2:
			web.loadUrl(participants);
			break;
		case R.id.imageView1:
			web.loadUrl(home);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if (web.canGoBack()) {
			web.goBack();
		} else {
			finish();
		}

	}

}
