package com.repo.list.ui.base


import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.repo.list.dialog.LoadingDialog
import com.repo.list.R
import com.repo.list.showToast

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> :
    AppCompatActivity(), BaseContract.View {

    abstract val layoutId: Int
    abstract val presenter: P
    abstract val view: V
    private var loadingDialog: LoadingDialog? = null

    @CallSuper
    open fun onActivityReady(savedInstanceState: Bundle?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        presenter.attachView(view)
        onActivityReady(savedInstanceState)
    }

    override fun onError(message: String) {
        showToast(message)
    }

    override fun onDatabaseError() {
        showToast(getString(R.string.database_error))
    }

    override fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.init(this)
        }
        if (!loadingDialog!!.isShowing) {
            loadingDialog!!.show()
        }
    }

    override fun hideLoading() {
        if (loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}