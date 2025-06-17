import axios from 'axios'

const instance = axios.create({
    baseURL: `${import.meta.env.VITE_API_URL}/v${import.meta.env.VITE_API_VERSION}`
})

export async function getAll(url: string){
    try {
        const response = await instance.get(url)
        
        return response.data
    } catch (error) {
        console.error('Error fetching categories:', error)
        throw error
    }
}