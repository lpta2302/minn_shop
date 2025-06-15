export function capitalizeWords(string: string) {
  return string.split(' ').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
}

export function toVND (value: number){
    return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })
}