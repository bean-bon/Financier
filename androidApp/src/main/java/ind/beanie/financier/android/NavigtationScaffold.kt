package ind.beanie.financier.android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.mongodb.kbson.ObjectId

@Composable
fun NavigationScaffold(
    transactionManager: TransactionManager = TransactionManager()
) {
    val navController = rememberNavController()
    Scaffold {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = "transactionList"
            ) {
                composable("transactionList") {
                    TransactionListView(
                        transactionManager,
                        onAddTransaction = { navController.navigate("addTransaction") }
                    ) { t ->
                        navController.navigate("transactionSummary/${t._id.toHexString()}")
                    }
                }
                composable("addTransaction") {
                    TransactionAdditionView { t ->
                        transactionManager.addTransaction(t)
                        navController.popBackStack()
                    }
                }
                composable(
                    "transactionSummary/{id}",
                    listOf(navArgument("id") { type = NavType.StringType } )
                ) { nb ->
                    nb.arguments?.getString("id")?.let { idString ->
                        val objectId = ObjectId(hexString = idString)
                        val transaction = transactionManager.getTransaction(objectId)
                        transaction?.let { tr ->
                            TransactionSummaryView(
                                tr,
                                onClose = { navController.popBackStack() }
                            ) {
                                transactionManager.removeTransaction(tr)
                                navController.popBackStack()
                            }
                        } ?: navController.popBackStack()
                    } ?: navController.popBackStack()
                }
            }
        }
    }

}