import customAxios from './customAxios'
import axios from 'axios'

// export async function get(url: string) {
//     try {
//         const response = await customAxios.get(url,
//             {
//                 headers: {
//                     Authorization: `Bearer ${getLocalstorage('accessToken')}`
//                 }
//             }
//         )

//         return response.data
//     } catch (error) {
//         console.error(error)
//         return error
//     }
// }

// export async function patch(url: string, body: object) {
//     try {
//         const response = await customAxios.patch(url, body)
//         return response.data
//     } catch (error) {
//         console.error(error);
//         return error

//     }
// }

// export async function post<T>(url: string, body: object | string) {
//     try {
//         console.log(customAxios.head);

//         const response = await customAxios.post<T>(url, body)
//         return response.data
//     } catch (error) {
//         console.error(error);
//         return error
//     }
// }

export async function get<T>(url: string, params?: unknown): Promise<T> {
  try {
    const response = await customAxios.get<T>(url, { params })
    return response.data
  } catch (error) {
    handleAxiosError(error)
  }
}

export async function post<T>(url: string, body?: unknown): Promise<T> {
  try {
    const response = await customAxios.post<T>(url, body)
    return response.data
  } catch (error) {
    handleAxiosError(error)
  }
}

export async function patch<T>(url: string, body?: unknown): Promise<T> {
  try {
    const response = await customAxios.patch<T>(url, body)
    return response.data
  } catch (error) {
    handleAxiosError(error)
  }
}

export async function del<T>(url: string): Promise<T> {
  try {
    const response = await customAxios.delete<T>(url)
    return response.data
  } catch (error) {
    handleAxiosError(error)
  }
}

// 🔧 Hàm xử lý lỗi chung
function handleAxiosError(error: unknown): never {
  if (axios.isAxiosError(error)) {
    // Có thể extract thêm thông tin từ response nếu cần
    const message = error.response?.data?.message || error.message
    throw new Error(message)
  }

  // Nếu không phải lỗi của axios
  throw new Error('Unexpected error')
}