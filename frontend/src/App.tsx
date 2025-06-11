import { Route, Routes } from "react-router"
import { managementRoutes } from "./constants"
import AdminLayout from "./pages/AdminLayout"
import Homepage from "./pages/home/Homepage"

function App() {
    return (
        <Routes >
            <Route path="/" Component={AdminLayout}>
                <Route index Component={Homepage} />
                <Route path="manage">
                    {
                        managementRoutes.map(route => (
                            <Route
                                path={route.path}
                                Component={route.component}
                            />
                        ))
                    }
                </Route>
            </Route>
        </Routes>
    )
}

export default App