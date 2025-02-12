'use client';

import { useState } from 'react';
import Link from 'next/link';

export default function Header() {
  const [isMenuOpen, setMenuOpen] = useState(false);

  const toggleMenu = () => setMenuOpen((prev) => !prev);

  return (
    <header className="bg-white shadow">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center py-6">
          {/* Logo / Nome da Aplicação */}
          <div className="flex-shrink-0">
            <Link href="/" className="text-2xl font-bold text-blue-600">
              AgroRadar
            </Link>
          </div>

          {/* Menu desktop */}
          <nav className="hidden md:flex space-x-10">
            <Link
              href="/dispositivos"
              className="text-base font-medium text-gray-500 hover:text-gray-900"
            >
              Dispositivos
            </Link>
            <Link
              href="/sensores"
              className="text-base font-medium text-gray-500 hover:text-gray-900"
            >
              Sensores
            </Link>
            <Link
              href="/gateways"
              className="text-base font-medium text-gray-500 hover:text-gray-900"
            >
              Gateways
            </Link>
          </nav>

          {/* Menu mobile: botão hamburguer */}
          <div className="md:hidden">
            <button
              onClick={toggleMenu}
              type="button"
              className="inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-gray-500 hover:bg-gray-100 focus:outline-none"
              aria-expanded={isMenuOpen}
            >
              <span className="sr-only">Abrir menu</span>
              {isMenuOpen ? (
                <svg
                  className="h-6 w-6"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M6 18L18 6M6 6l12 12"
                  />
                </svg>
              ) : (
                <svg
                  className="h-6 w-6"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M4 6h16M4 12h16M4 18h16"
                  />
                </svg>
              )}
            </button>
          </div>
        </div>
      </div>

      {/* Menu mobile aberto */}
      {isMenuOpen && (
        <div className="md:hidden">
          <div className="px-2 pt-2 pb-3 space-y-1">
            <Link
              href="/dispositivos"
              className="block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:bg-gray-50"
            >
              Dispositivos
            </Link>
            <Link
              href="/sensores"
              className="block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:bg-gray-50"
            >
              Sensores
            </Link>
            <Link
              href="/gateways"
              className="block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:bg-gray-50"
            >
              Gateways
            </Link>
          </div>
        </div>
      )}
    </header>
  );
}
