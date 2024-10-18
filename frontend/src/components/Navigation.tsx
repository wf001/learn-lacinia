import Link from 'next/link'
import { useRouter } from 'next/router'

export const Navigation = () => {
  const router = useRouter()
  return (
    <div className={'items-align mb-4'}>
      <Link className="text-blue-400 mr-3" href="/">
        Home
      </Link>
      <span
        className="text-blue-400 cursor-pointer "
        onClick={() => router.back()}
      >
        Back
      </span>
    </div>
  )
}
