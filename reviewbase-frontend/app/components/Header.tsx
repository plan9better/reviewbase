import Link from 'next/link';
import { useUser } from '../../hooks/useUser';

export default function Header() {
  const { user, logout } = useUser();

  return (
      <header className="bg-gray-800 shadow-md">
        <nav className="container mx-auto px-6 py-4">
          <div className="flex items-center justify-between">
            <Link href="/" className="text-2xl font-bold text-blue-500">
              ReviewBase
            </Link>
            <div className="flex items-center space-x-4">
              <Link href="/" className="hover:text-blue-500 transition-colors">
                Home
              </Link>
              <Link href="/reviews" className="hover:text-blue-500 transition-colors">
                Reviews
              </Link>
              <Link href="/recommendations" className="hover:text-blue-500 transition-colors">
                Recommendations
              </Link>
              {user ? (
                  <div className="flex items-center space-x-4">
                    <span className="text-white">Welcome, {user.username}</span>
                    <button
                        onClick={logout}
                        className="hover:text-red-500 transition-colors"
                    >
                      Logout
                    </button>
                  </div>
              ) : (
                  <Link href="/login" className="hover:text-blue-500 transition-colors">
                    Login
                  </Link>
              )}
            </div>
          </div>
        </nav>
      </header>
  );
}
