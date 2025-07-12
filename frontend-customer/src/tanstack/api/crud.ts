import axios from 'axios'

const instance = axios.create({
    baseURL: `${import.meta.env.VITE_API_URL}/v${import.meta.env.VITE_API_VERSION}`
})

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export async function get(url: string){
    try {
        const response = await instance.get(url)
        
        return response.data
    } catch (error) {
        console.error(error)
        throw error
    }
}

export async function patch(url:string, body: object) {
    try {
        const response = await instance.patch(url, body)
        return response.data
    } catch (error) {
        console.error(error);
        throw error
    }
}