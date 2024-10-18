import Link from 'next/link'

export const TextLink = ({ href, text }: { href: string; text: string }) => (
  <Link className="text-blue-400" href={href}>
    {text}
  </Link>
)
