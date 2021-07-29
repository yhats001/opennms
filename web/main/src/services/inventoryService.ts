import { rest } from './axiosInstance'
import { IPRange, IPRangeResponse, SNMPDetectRequest, SNMPDetectResponse } from "@/types"

const endpoint = '/nodediscover'

const scanIPRanges = async (IPRanges: IPRange[]): Promise<IPRangeResponse[] | false> => {
	try {
		const resp = await rest.post(`${endpoint}/scan`, IPRanges)
		return resp.data
	} catch (err) {
		return false
	}
}

const detectSNMPAvailable = async (SNMPDetectRequests: SNMPDetectRequest[]): Promise<SNMPDetectResponse[] | false> => {
	try {
		const resp = await rest.post(`${endpoint}/scan`, SNMPDetectRequests)
		return resp.data
	} catch (err) {
		return false
	}
}

export {
	scanIPRanges,
	detectSNMPAvailable
}