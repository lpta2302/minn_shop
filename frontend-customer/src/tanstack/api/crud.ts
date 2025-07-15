import { getLocalstorage } from '@/lib/localstorage'
import customAxios from './customAxios'

export async function get(url: string) {
    try {
        const response = await customAxios.get(url,
            {
                headers: {
                    Authorization: `Bearer ${getLocalstorage('accessToken')}`
                }
            }
        )

        return response.data
    } catch (error) {
        console.error(error)
        throw error
    }
}

export async function patch(url: string, body: object) {
    try {
        const response = await customAxios.patch(url, body)
        return response.data
    } catch (error) {
        console.error(error);
        throw error
    }
}

export async function post<T>(url: string, body: object | string | null) {
    try {
        console.log(customAxios.head);

        const response = await customAxios.post<T>(url, body)
        return response.data
    } catch (error) {
        console.error(error);
        throw error
    }
}