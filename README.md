### Android Kotlin + MVP + RxJava2 + Base Request + SQLite Helper

# MVP
### BaseContact
```kotlin
class BaseContact {

    interface View {
        fun onDatabaseError()
        fun onError(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter<T : View> {
        fun attachView(view: T)
        fun addDisposable(disposable: Disposable)
        fun detachView()
    }
}
```

### BaseActivity
```kotlin
abstract class BaseActivity<V : BaseContact.View, P : BaseContact.Presenter<V>> :
    AppCompatActivity(), BaseContact.View {

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

//override methods...

//....
}
```
### BasePresenter
```kotlin
open class BasePresenter<T : BaseContact.View> : BaseContact.Presenter<T> {

    protected var view: T? = null
    private lateinit var disposables : CompositeDisposable

    override fun attachView(view: T) {
        this.view = view
        disposables = CompositeDisposable()
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
    override fun detachView() {
        view = null
        disposables.dispose()
    }

}
```
#### Other Activity
```kotlin
class SearchRepoActivity :
    BaseActivity<SearchRepoContact.View, SearchRepoContact.Presenter>(), SearchRepoContact.View,
    RecyclerItemClickListener<GithubUserRepos> {

    override val layoutId = R.layout.activity_search_repo
    override val presenter: SearchRepoContact.Presenter = SearchRepoPresenter()
    override val view: SearchRepoContact.View = this
	
	//....
```

# SQL Helper
#### Create Sql
```kotlin
open class DatabaseCreator(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        createFavoriteTable(db)
    }

    private fun createFavoriteTable(db: SQLiteDatabase?) {
        db?.execSQL("Create Table if not exists ${FavoriteTable.TABLE_NAME}(${FavoriteTable.REPO_ID} INTEGER);");
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            dropTable(db, FavoriteTable.TABLE_NAME)
        }
        onCreate(db)
    }

    private fun dropTable(db: SQLiteDatabase?, tableName: String) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
    }
}
```
#### Database Transactions
```kotlin
object DatabaseHelper : DatabaseCreator(GitApplication.instance) {

    private fun addFavorite(id: Int): Boolean {
        return writableDatabase.insert(
            FavoriteTable.TABLE_NAME,
            null,
            ContentValues().apply {
                put(FavoriteTable.REPO_ID, id)
            }) > 0
    }

    private fun removeFavorite(id: Int): Boolean {
        return writableDatabase.delete(
            FavoriteTable.TABLE_NAME,
            "${FavoriteTable.REPO_ID}=?",
            arrayOf(id.toString())
        ) > 0
    }

    fun toggleFavorite(id: Int, isFavorite: Boolean): Boolean {
        return if (isFavorite) {
            removeFavorite(id)
        } else {
            addFavorite(id)
        }
    }

    fun isFavorite(id: Int): Boolean {
        val usersSelectQuery = String.format(
            "SELECT * FROM %s WHERE %s = ?",
            FavoriteTable.TABLE_NAME,
            FavoriteTable.REPO_ID
        )
        val cursor = writableDatabase.rawQuery(usersSelectQuery, arrayOf(id.toString()))
        val isFavorite = cursor.moveToFirst()
        cursor.close()
        return isFavorite
    }
}
```


# Base Request
####ApiResponse
```kotlin
interface ApiResponse<T> {
    fun onSuccessResponse(response: T)
    fun onFailResponse(errorMessage: String)
}
```

#### Request
```kotlin
fun <T> disposable(observable: Observable<Response<T>>, apiResponse: ApiResponse<T>): Disposable =
        observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        apiResponse.onSuccessResponse(response.body()!!)
                    } else {
                        apiResponse.onFailResponse("null")
                    }
                } else {
                    apiResponse.onFailResponse(response.message())
                }
            }, { t: Throwable? ->
                t?.let {
                    apiResponse.onFailResponse("${it.message}")
                } ?: run {
                    apiResponse.onFailResponse("null message")
                }
            })
```
