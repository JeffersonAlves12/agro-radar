"use client"
import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

export default function Home() {
  const router = useRouter();
  const [formData, setFormData] = useState({
    email: "",
    senha: "",
    remember: false,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  // Função getLogin usando fetch
  const getLogin = async (email, senha) => {
    const body = JSON.stringify({ email, senha }); // Criar o objeto LoginDto
    console.log("Dados enviados:", body); // Para depuração

    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          'accept': '*/*',
          'Content-Type': 'application/json',
        },
        mode: 'cors', // Adicionado para garantir CORS
        body: body,
      });

      if (!response.ok) {
        console.log("!ok")
        const errorData = await response.json(); // Captura resposta de erro do backend
        throw new Error(errorData.message || `Erro ${response.status}`);
      }

      const data = await response.json();
      console.log("Resposta do servidor:", data); // Para depuração
      return data; // Retorna o LoginResponseDto
    } catch (error) {
      console.error("Erro na requisição:", error);
      throw new Error("Não foi possível conectar ao servidor. Verifique sua rede.");
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    console.log("Dados do formulário:", formData); // Para depuração

    try {
      // Substituir a chamada do repository pela função getLogin
      const response = await getLogin(formData.email, formData.senha);
      console.warn("Resposta do login:", response);

      // Se chegou aqui, o login foi bem-sucedido
      const { token } = response;

      if (token) {
        // Armazena o token
        localStorage.setItem("token", token);

        if (formData.remember) {
          localStorage.setItem("rememberedEmail", formData.email);
        } else {
          localStorage.removeItem("rememberedEmail");
        }

        // Redireciona para a página de mapas
        router.push("/");
      } else {
        throw new Error("Token não recebido do servidor");
      }
    } catch (error) {
      console.error("Erro detalhado:", error);

      if (error instanceof Error) {
        setError(error.message || "Erro desconhecido ao fazer login.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-[#f3f4f6]">
      <div className="max-w-sm w-full bg-white p-8 rounded-lg shadow-lg">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-semibold text-gray-800">AgroRadar</h1>
          <p className="text-gray-600">Use sua conta AgroRadar</p>
        </div>

        <form onSubmit={handleLogin} className="space-y-6">
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">
              Email ou telefone
            </label>
            <input
              type="email"
              id="email"
              name="email"
              className="mt-2 p-3 w-full border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              placeholder="Entre com seu email"
              value={formData.email}
              onChange={handleInputChange}
              required
              aria-required="true"
            />
          </div>

          <div>
            <label htmlFor="senha" className="block text-sm font-medium text-gray-700">
              Senha
            </label>
            <input
              type="password"
              id="senha"
              name="senha"
              className="mt-2 p-3 w-full border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              placeholder="Entre com sua senha"
              value={formData.senha}
              onChange={handleInputChange}
              required
              aria-required="true"
            />
          </div>

          {error && (
            <div className="text-red-500 text-sm p-3 bg-red-50 rounded-md border border-red-200">
              {error}
            </div>
          )}

          <div className="flex items-center justify-between">
            <div className="flex items-center">
              <input
                type="checkbox"
                id="remember"
                name="remember"
                checked={formData.remember}
                onChange={handleInputChange}
                className="h-4 w-4 border-gray-300 rounded text-blue-600 focus:ring-blue-500"
                aria-checked="false"
              />
              <label htmlFor="remember" className="ml-2 text-sm text-gray-600">
                Lembrar de mim
              </label>
            </div>
            <Link href="/forgot-password" className="text-sm text-blue-600 hover:underline">
              Esqueceu a senha?
            </Link>
          </div>

          <button
            type="submit"
            disabled={loading}
            className={`w-full py-3 px-4 text-white rounded-md transition-all focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 
              ${loading ? "bg-blue-400 cursor-not-allowed" : "bg-blue-600 hover:bg-blue-700"}`}
          >
            {loading ? (
              <span className="flex items-center justify-center">
                <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Entrando...
              </span>
            ) : (
              "Entrar"
            )}
          </button>
        </form>

        <div className="mt-8 text-center">
          <p className="text-sm text-gray-600">
            Não tem uma conta?{" "}
            <Link href="/register" className="text-blue-600 hover:underline">
              Crie uma
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}