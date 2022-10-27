package pro.breez.bfsut.util.permission

interface PermissionCheckerBuilder {
    fun setPermission(permission: String): PermissionCheckerBuilder
    fun setPermissions(vararg permissions: String): PermissionCheckerBuilder
    fun setOnGranted(onGranted: () -> Unit): PermissionCheckerBuilder
    fun setOnBlocked(onBlocked: (ArrayList<String>?) -> Boolean): PermissionCheckerBuilder
    fun setOnDenied(onDenied: (ArrayList<String>?) -> Unit): PermissionCheckerBuilder
    fun setOnJustBlocked(onJustBlocked: (ArrayList<String>?, ArrayList<String>?) -> Unit): PermissionCheckerBuilder
}