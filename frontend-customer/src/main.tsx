import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App'
import { BrowserRouter as Router } from 'react-router'
import { CartProvider } from './context/CartContext'
import {
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query'
import AuthProvider from './context/AuthContext'
import { Toaster } from 'sonner'

const queryClient = new QueryClient()

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
        <CartProvider>
            <AuthProvider>
                <Router>
                    <App />
                    <Toaster/>
                </Router>
            </AuthProvider>
        </CartProvider>
    </QueryClientProvider>
  </StrictMode>,
)
