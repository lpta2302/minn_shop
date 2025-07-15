import { getLocalstorage, removeLocalstorage, setLocalstorage } from "@/lib/localstorage";
import { setBearerToken } from "@/tanstack/api/customAxios";
import { useGetCurrentUser, useRefreshToken } from "@/tanstack/queries/authQueries";
import type { Customer } from "@/types/auth";
import { useState, useEffect, useContext, type ReactElement, useCallback } from "react";
import { createContext } from "react";

const INIT_USER: Customer = {
    id: 0,
    email: '',
    firstName: '',
    lastName: '',
    fullName: '',
    role: [],
    accountId: 0
}

export type AuthContextType = {
  user: Customer;
  isAuthenticated: boolean;
  isLoading: boolean;
  setUser?: React.Dispatch<React.SetStateAction<Customer>>;
  setIsAuthenticated?: React.Dispatch<React.SetStateAction<boolean>>;
  checkAuthUser?: () => Promise<boolean>;
  logout?: () => void;
};

const INIT_STATE = {
    user: INIT_USER,
    isAuthenticated: false,
    isLoading: false
};

const AuthContext = createContext<AuthContextType>(INIT_STATE)

export default function AuthProvider({ children } : {children: ReactElement}) {
    // const navigate = useNavigate();
    const [user, setUser] = useState<Customer>(INIT_USER);
    const [token, setToken] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    
    const {mutateAsync: getAuthTokens} = useRefreshToken()
    const {refetch: getCurrentUser} = useGetCurrentUser()

    const accessToken = getLocalstorage("accessToken")
    const refreshToken = getLocalstorage("refreshToken")

    const checkAuthUser = useCallback(async () =>{
        try {
            if (accessToken) {
                setBearerToken(accessToken);
                const {data: user} = await getCurrentUser();
                
                if (!user?.id) throw new Error("No user");
                
                setUser(user);
                setIsAuthenticated(true);
                return true;
            }

            // If no accessToken but has refreshToken
            if (refreshToken) {
                const authResponse = await getAuthTokens(refreshToken);
                if (!authResponse?.accessToken) throw new Error("No token");

                setLocalstorage("accessToken", authResponse.accessToken);
                setBearerToken(authResponse.accessToken);

                const {data: user} = await getCurrentUser();
                console.log(user);
                

                if (!user?.id) throw new Error("No user");

                setUser(user);
                setIsAuthenticated(true);
                return true;
            }
            return true;
        } catch (error) {
            console.log(error);
            return false;
        } finally {
            setIsLoading(false);
        }
    }, [accessToken, refreshToken, getCurrentUser, getAuthTokens])

    const logout = () => {
        removeLocalstorage('accessToken');
        setUser(INIT_USER);
        setIsAuthenticated(false);
        // setBearerToken(null);
        // toaster('Đăng xuất thành công', { variant: 'success' });
    };

    useEffect(() => {
        if(accessToken === null && refreshToken === null){
            return 
        } else {
            console.log("check");
            
            const fetchUser = async () => {
                await checkAuthUser();
                // Xử lý tiếp sau khi check xong nếu cần
            };
            fetchUser()
        }
    }, [accessToken, checkAuthUser, refreshToken]);

    const value = {
        user,
        token,
        isAuthenticated,
        isLoading,
        setUser,
        setToken,
        setIsAuthenticated,
        checkAuthUser,
        logout,
    }

    return (
        <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
    )
}

export const useAuthContext = () => useContext(AuthContext);