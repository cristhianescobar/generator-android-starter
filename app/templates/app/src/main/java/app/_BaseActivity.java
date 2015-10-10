package <%= appPackage %>.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import <%= appPackage %>.toolbar.ToolbarOwner;

import mortar.MortarScope;


abstract public class BaseActivity extends AppCompatActivity implements ToolbarOwner.Activity {

    protected ToolbarOwner toolbarOwner;

    protected MortarScope mortarScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.toolbarOwner = new ToolbarOwner();
    }
}
