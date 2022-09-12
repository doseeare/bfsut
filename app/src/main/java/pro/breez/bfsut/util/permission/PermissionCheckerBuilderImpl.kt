package pro.breez.bfsut.util.permission


import java.util.*

class PermissionCheckerBuilderImpl : PermissionCheckerBuilder {

    private lateinit var permissions: Array<out String>
    private var onGranted: (() -> Unit)? = null
    private var onBlocked: ((ArrayList<String>?) -> Boolean)? = null
    private var onDenied: ((ArrayList<String>?) -> Unit)? = null
    private var onJustBlocked: ((ArrayList<String>?, ArrayList<String>?) -> Unit)? = null

    override fun setPermission(permission: String): PermissionCheckerBuilder {
        permissions = arrayOf(permission)
        return this
    }

    override fun setPermissions(vararg permissions: String): PermissionCheckerBuilder {
        this.permissions = permissions
        return this
    }

    override fun setOnGranted(onGranted: () -> Unit): PermissionCheckerBuilder {
        this.onGranted = onGranted
        return this
    }

    override fun setOnBlocked(onBlocked: (ArrayList<String>?) -> Boolean): PermissionCheckerBuilder {
        this.onBlocked = onBlocked
        return this
    }

    override fun setOnDenied(onDenied: (ArrayList<String>?) -> Unit): PermissionCheckerBuilder {
        this.onDenied = onDenied
        return this
    }

    override fun setOnJustBlocked(onJustBlocked: (ArrayList<String>?, ArrayList<String>?) -> Unit): PermissionCheckerBuilder {
        this.onJustBlocked = onJustBlocked
        return this
    }

}