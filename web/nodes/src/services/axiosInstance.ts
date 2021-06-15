import axios from 'axios'

const portal = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  withCredentials: true
})

export {
  portal
}
