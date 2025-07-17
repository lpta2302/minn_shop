import axios from 'axios'

const axiosInstance = (function () {
    let token = "";
    axios.defaults.baseURL = `${import.meta.env.VITE_API_URL}/v${import.meta.env.VITE_API_VERSION}`;

    const setBearerToken = (newToken: string) => {
        token = newToken;
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        axios.defaults.withCredentials=false
    };
    axios.interceptors.request.use((config) => {
        return config;
    });

    return {
        setBearerToken,
        axios
    };
})();

export default axiosInstance.axios

export const setBearerToken = axiosInstance.setBearerToken;