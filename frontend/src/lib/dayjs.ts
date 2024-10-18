import dayjs from 'dayjs'

export const toYYYYMMDD = (datetime: string | null | undefined) => {
  if (!datetime) return '9999-12-31'
  return dayjs(datetime).format('YYYY-MM-DD')
}
